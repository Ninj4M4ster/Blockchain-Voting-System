package chaincode

import (
	"encoding/json"
	"fmt"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

// SmartContract provides functions for managing an Asset
type SmartContract struct {
	contractapi.Contract
}

// Voter includes the public key of voter who is allowed to take part
// signature is empty by default, correct signature indicates that the voter already
// used his vote and cannot do sa again

type Voter struct {
	PublicKey string `json:"PublicKey"`
	Signature string `json:"Signature"`
}

// InitVoters adds sample voters fo testing purposes
func (s *SmartContract) InitVoters(ctx contractapi.TransactionContextInterface) error {
	voters := []Voter{
		{PublicKey: "ABCDEF", Signature: ""},
		{PublicKey: "QWERTY", Signature: ""},
		{PublicKey: "XYZ", Signature: ""},
		{PublicKey: "KLMNOP", Signature: ""},
	}

	for _, voter := range voters {
		voterJSON, err := json.Marshal(voter)
		if err != nil {
			return err
		}

		err = ctx.GetStub().PutState(voter.PublicKey, voterJSON)
		if err != nil {
			return fmt.Errorf("failed to put to world state. %v", err)
		}
	}

	return nil
}

// AddVoter adds a voter granted that no voter with given public key is present
func (s *SmartContract) AddVoter(ctx contractapi.TransactionContextInterface, publicKey string) error {
	exists, err := s.VoterExists(ctx, publicKey)
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

	voterJSON, err := json.Marshal(voter)
	if err != nil {
		return err
	}

	err = ctx.GetStub().PutState(publicKey, voterJSON)
	if err != nil {
		return fmt.Errorf("failed to put to world state. %v", err)
	}

	return nil
}

// GetAllVoters returns all voters found in world state
func (s *SmartContract) GetAllVoters(ctx contractapi.TransactionContextInterface) ([]*Voter, error) {
	// range query with empty string for startKey and endKey does an
	// open-ended query of all assets in the chaincode namespace.
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

		var voter Voter
		err = json.Unmarshal(queryResponse.Value, &voter)
		if err != nil {
			return nil, err
		}
		voters = append(voters, &voter)
	}
	return voters, nil
}

// VoterExists returns true when voter with given public key exists in world state
func (s *SmartContract) VoterExists(ctx contractapi.TransactionContextInterface, publicKey string) (bool, error) {
	voterJSON, err := ctx.GetStub().GetState(publicKey)
	if err != nil {
		return false, fmt.Errorf("failed to read from world state: %v", err)
	}

	return voterJSON != nil, nil
}

// CanVote return true when voter exists and hasn't voted
func (s *SmartContract) CanVote(ctx contractapi.TransactionContextInterface, publicKey string) (bool, error) {
	voterJSON, err := ctx.GetStub().GetState(publicKey)
	if err != nil {
		return false, fmt.Errorf("failed to read from world state: %v", err)
	}

	if voterJSON == nil {
		return false, nil
	}

	var voter Voter
	err = json.Unmarshal(voterJSON, &voter)
	if err != nil {
		return false, err
	}

	return voter.Signature == "", nil
}

// AddSignature updates an existing voter in the world state with signature, implying that vote was submitted.
func (s *SmartContract) AddSignature(ctx contractapi.TransactionContextInterface, publicKey string, signature string) error {
	// check if voter exists
	exists, err := s.VoterExists(ctx, publicKey)
	if err != nil {
		return err
	}
	if !exists {
		return fmt.Errorf("voter %s does not have the right to vote", publicKey)
	}

	// check if voter hasn't already used his right to vote
	canVote, err := s.CanVote(ctx, publicKey)
	if err != nil {
		return err
	}
	if !canVote {
		return fmt.Errorf("voter %s has already cast their vote", publicKey)
	}

	// TODO check if signature is correct

	// overwriting original asset with new asset
	voter := Voter{
		PublicKey: publicKey,
		Signature: signature,
	}
	voterJSON, err := json.Marshal(voter)
	if err != nil {
		return err
	}

	return ctx.GetStub().PutState(publicKey, voterJSON)
}

func (s *SmartContract) CountSignatures(ctx contractapi.TransactionContextInterface) (int, error) {
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return 0, err
	}
	defer resultsIterator.Close()

	numberOfSignatures := 0
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return 0, err
		}

		var voter Voter
		err = json.Unmarshal(queryResponse.Value, &voter)
		if err != nil {
			return 0, err
		}
		if voter.Signature != "" {
			numberOfSignatures += 1
		}
	}
	return numberOfSignatures, nil
}
