package chaincode

import (
	"encoding/json"
	"fmt"
	"strconv"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

// SmartContract provides functions for managing an Asset
type SmartContract struct {
	contractapi.Contract
}

type Vote struct {
	CandidateID string `json:"CandidateID"`
	VoteID      int    `json:"VoteID"`
}

// from "github.com/hyperledger/fabric/blob/main/common/util/utils.go"
// ToChaincodeArgs converts string args to []byte args
func ToChaincodeArgs(args ...string) [][]byte {
	bargs := make([][]byte, len(args))
	for i, arg := range args {
		bargs[i] = []byte(arg)
	}
	return bargs
}

func (s *SmartContract) CastVote(ctx contractapi.TransactionContextInterface, candidateID string) error {
	// TODO good method for unique names
	// current implementation is unsafe
	numberOfVotes, err := s.VoteCount(ctx)
	if nil != err {
		return err
	}

	// creating new vote
	vote := Vote{
		CandidateID: candidateID,
		VoteID:      numberOfVotes,
	}

	voteJSON, err := json.Marshal(vote)
	if err != nil {
		return err
	}

	err = ctx.GetStub().PutState(strconv.Itoa(numberOfVotes), voteJSON)
	if err != nil {
		return fmt.Errorf("failed to put to world state. %v", err)
	}

	return nil
}

// GetAllVotes returns all votes found in world state
func (s *SmartContract) GetAllVotes(ctx contractapi.TransactionContextInterface) ([]*Vote, error) {
	// range query with empty string for startKey and endKey does an
	// open-ended query of all assets in the chaincode namespace.
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return nil, err
	}
	defer resultsIterator.Close()

	var votes []*Vote
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return nil, err
		}

		var vote Vote
		err = json.Unmarshal(queryResponse.Value, &vote)
		if err != nil {
			return nil, err
		}
		votes = append(votes, &vote)
	}
	return votes, nil
}

func (s *SmartContract) VoteCount(ctx contractapi.TransactionContextInterface) (int, error) {
	resultsIterator, err := ctx.GetStub().GetStateByRange("", "")
	if err != nil {
		return 0, err
	}
	defer resultsIterator.Close()

	numberOfVotes := 0
	for resultsIterator.HasNext() {
		_, err := resultsIterator.Next()
		if err != nil {
			return 0, err
		}
		numberOfVotes += 1
	}
	return numberOfVotes, nil
}

func (s *SmartContract) CheckValidity(ctx contractapi.TransactionContextInterface) (bool, error) {
	numberOfVotes, err := s.VoteCount(ctx)
	if err != nil {
		return false, err
	}

	invokeArgs := ToChaincodeArgs("CountSignatures")
	response := ctx.GetStub().InvokeChaincode("voters", invokeArgs, "voters")
	numberOfSignatures, err := strconv.Atoi(string(response.GetPayload()[:]))
	if err != nil {
		return false, err
	}

	return numberOfVotes == numberOfSignatures, nil
}

func (s *SmartContract) Test(ctx contractapi.TransactionContextInterface) (int, error) {
	invokeArgs := ToChaincodeArgs("CountSignatures")
	response := ctx.GetStub().InvokeChaincode("voters", invokeArgs, "voters")
	payload := string(response.GetPayload()[:])
	return strconv.Atoi(payload)
}
