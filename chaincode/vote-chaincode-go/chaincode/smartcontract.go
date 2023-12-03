package chaincode

import (
	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

// SmartContract provides functions for managing an Asset
type SmartContract struct {
	contractapi.Contract
}

// enum for struct type
type Type int64

const (
	INFO Type = iota
	VOTER
	VOTE
)

// this will hold info about the election (probably)
type ElectionInfo struct {
}

type Vote struct {
	CandidateID string `json:"CandidateID"`
	VoteID      int    `json:"VoteID"`
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
	return nil
}

func (s *SmartContract) RemoveVoter(ctx contractapi.TransactionContextInterface, publicKey string) error {
	return nil
}

func (s *SmartContract) GetVoters(ctx contractapi.TransactionContextInterface) ([]*Voter, error) {
	var voters []*Voter
	voter := Voter{PublicKey: "dh39dh92hd", Signature: ""}
	voters = append(voters, &voter)
	voter = Voter{PublicKey: "f82hf248hf", Signature: "jf3hf9hw934f"}
	voters = append(voters, &voter)
	return voters, nil
}

func (s *SmartContract) HasVotingRights(cts contractapi.TransactionContextInterface, publicKey string) (bool, error) {
	if publicKey == "dh39dh92hd" || publicKey == "f82hf248hf" {
		return true, nil
	}
	return false, nil
}

func (s *SmartContract) HasVoted(ctx contractapi.TransactionContextInterface, publicKey string) (bool, error) {
	if publicKey == "f82hf248hf" {
		return true, nil
	}
	return false, nil
}

func (s *SmartContract) CastVote(ctx contractapi.TransactionContextInterface, publicKey, signature string) (bool, error) {
	return true, nil
}

func (s *SmartContract) FinalizeVote(ctx contractapi.TransactionContextInterface, candidate string, additionalInfo string) error {
	return nil
}

func (s *SmartContract) GetResults(ctx contractapi.TransactionContextInterface) ([]*Result, error) {
	var results []*Result
	result := Result{CandidateID: "Candidate 1", VoteCount: 12345}
	results = append(results, &result)
	result = Result{CandidateID: "Candidate 2", VoteCount: 6789}
	results = append(results, &result)
	return results, nil
}

func (s *SmartContract) ValidateElection(ctx contractapi.TransactionContextInterface) (bool, error) {
	return true, nil
}
