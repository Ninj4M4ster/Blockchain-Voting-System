package chaincode

import (
	"encoding/json"
	"fmt"
	"strconv"
	"time"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
	"github.com/mitchellh/mapstructure"
)

// SmartContract provides functions for managing an Asset
type SmartContract struct {
	contractapi.Contract
}

// struct for saving entries to world state
type JSONData struct {
	Type string      `json:"type"`
	Data interface{} `json:"data"`
}

// this will hold info about the election (probably)
type ElectionInfo struct {
}

type Vote struct {
	CandidateID string `json:"CandidateID"`
	VoteID      string `json:"VoteID"`
}

type Voter struct {
	PublicKey string `json:"PublicKey"`
	Signature string `json:"Signature"`
}

type Result struct {
	CandidateID string
	VoteCount   int
}

func (s *SmartContract) InitElection(ctx contractapi.TransactionContextInterface, voteInfo string) error {
	return nil
}

func (s *SmartContract) GetElectionStatus(ctx contractapi.TransactionContextInterface) (int, error) {
	return 0, nil
}

func (s *SmartContract) AddVoter(ctx contractapi.TransactionContextInterface, publicKey string) error {
	exists, err := s.HasVotingRights(ctx, publicKey)
	if err != nil {
		return fmt.Errorf("failed to read from world state: %v", err)
	}
	if exists {
		return fmt.Errorf("voter %s is already present in world state", publicKey)
	}

	voter := Voter{
		PublicKey: publicKey,
		Signature: "",
	}

	voterData := JSONData{
		Type: "voter",
		Data: voter,
	}

	voterDataJSON, err := json.Marshal(voterData)
	if err != nil {
		return err
	}

	err = ctx.GetStub().PutState(publicKey, voterDataJSON)
	if err != nil {
		return fmt.Errorf("failed to put to world state. %v", err)
	}

	return nil
}

func (s *SmartContract) RemoveVoter(ctx contractapi.TransactionContextInterface, publicKey string) error {
	exists, err := s.HasVotingRights(ctx, publicKey)
	if err != nil {
		return fmt.Errorf("failed to read from world state: %v", err)
	}
	if !exists {
		return fmt.Errorf("voter %s doesn't exist", publicKey)
	}

	err = ctx.GetStub().DelState(publicKey)
	if err != nil {
		return fmt.Errorf("failed writing to world state")
	}

	return nil
}

func (s *SmartContract) GetVoters(ctx contractapi.TransactionContextInterface) ([]*Voter, error) {
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return nil, err
	}
	defer resultsIterator.Close()

	var voters []*Voter
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return nil, err
		}

		var jsonData JSONData
		err = json.Unmarshal(queryResponse.Value, &jsonData)
		if err != nil {
			return nil, err
		}

		if jsonData.Type == "voter" {
			var voter Voter
			err := mapstructure.Decode(jsonData.Data, &voter)
			if err != nil {
				return nil, err
			}
			voters = append(voters, &voter)
		}
	}

	return voters, nil
}

func (s *SmartContract) HasVotingRights(ctx contractapi.TransactionContextInterface, publicKey string) (bool, error) {
	voterJSON, err := ctx.GetStub().GetState(publicKey)
	if err != nil {
		return false, fmt.Errorf("failed to read from world state: %v", err)
	}

	return voterJSON != nil, nil
}

func (s *SmartContract) HasVoted(ctx contractapi.TransactionContextInterface, publicKey string) (bool, error) {
	jsonData, err := ctx.GetStub().GetState(publicKey)
	if err != nil {
		return false, fmt.Errorf("failed to read from world state: %v", err)
	}

	if jsonData == nil {
		return false, nil
	}

	var data JSONData
	err = json.Unmarshal(jsonData, &data)
	if err != nil {
		return false, err
	}

	if data.Type != "voter" {
		return false, fmt.Errorf("file system problem")
	}

	var voter Voter
	err = mapstructure.Decode(data.Data, &voter)
	if err != nil {
		return false, err
	}

	return voter.Signature != "", nil
}

func (s *SmartContract) CastVote(ctx contractapi.TransactionContextInterface, publicKey, signature string) (bool, error) {
	// check if voter exists
	exists, err := s.HasVotingRights(ctx, publicKey)
	if err != nil {
		return false, err
	}
	if !exists {
		return false, fmt.Errorf("voter %s does not have the right to vote", publicKey)
	}

	// check if voter hasn't already used his right to vote
	hasVoted, err := s.HasVoted(ctx, publicKey)
	if err != nil {
		return false, err
	}
	if hasVoted {
		return false, fmt.Errorf("voter %s has already cast their vote", publicKey)
	}

	// TODO check if signature is correct

	// overwriting original asset with new asset
	voter := Voter{
		PublicKey: publicKey,
		Signature: signature,
	}

	voterData := JSONData{
		Type: "voter",
		Data: voter,
	}

	voterDataJSON, err := json.Marshal(voterData)
	if err != nil {
		return false, err
	}

	err = ctx.GetStub().PutState(publicKey, voterDataJSON)
	if err != nil {
		return false, fmt.Errorf("failed to put to world state. %v", err)
	}

	return true, nil
}

func (s *SmartContract) FinalizeVote(ctx contractapi.TransactionContextInterface, candidate string, additionalInfo string) error {
	txTimestamp, err := ctx.GetStub().GetTxTimestamp()
	if err != nil {
		return err
	}
	timestamp := time.Unix(txTimestamp.GetSeconds(), int64(txTimestamp.GetNanos())).UnixNano()

	// generate name
	voteID := strconv.Itoa(int(timestamp))

	vote := Vote{
		CandidateID: candidate,
		VoteID:      voteID,
	}

	voteData := JSONData{
		Type: "vote",
		Data: vote,
	}

	voteDataJSON, err := json.Marshal(voteData)
	if err != nil {
		return err
	}

	err = ctx.GetStub().PutState(voteID, voteDataJSON)
	if err != nil {
		return fmt.Errorf("failed to put to world state. %v", err)
	}

	return nil
}

func (s *SmartContract) GetResults(ctx contractapi.TransactionContextInterface) ([]*Result, error) {
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return nil, err
	}
	defer resultsIterator.Close()

	m := make(map[string]int)
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return nil, err
		}

		var jsonData JSONData
		err = json.Unmarshal(queryResponse.Value, &jsonData)
		if err != nil {
			return nil, err
		}

		if jsonData.Type == "vote" {
			var vote Vote
			err := mapstructure.Decode(jsonData.Data, &vote)
			if err != nil {
				return nil, err
			}
			m[vote.CandidateID] += 1
		}
	}

	var results []*Result
	for candidate, voteCount := range m {
		result := Result{CandidateID: candidate, VoteCount: voteCount}
		results = append(results, &result)
	}

	return results, nil
}

func (s *SmartContract) ValidateElection(ctx contractapi.TransactionContextInterface) (bool, error) {
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return false, err
	}
	defer resultsIterator.Close()

	voteCount := 0
	signatureCount := 0

	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return false, err
		}

		var jsonData JSONData
		err = json.Unmarshal(queryResponse.Value, &jsonData)
		if err != nil {
			return false, err
		}

		if jsonData.Type == "voter" {
			var voter Voter
			err := mapstructure.Decode(jsonData.Data, &voter)
			if err != nil {
				return false, err
			}
			// check signature validity
			if voter.Signature != "" {
				signatureCount += 1
			}
		}

		if jsonData.Type == "vote" {
			voteCount += 1
		}
	}

	return voteCount == signatureCount, nil
}
