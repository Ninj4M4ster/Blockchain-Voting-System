package main

import (
	"log"

	"vote/chaincode"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

func main() {
	votingChaincode, err := contractapi.NewChaincode(&chaincode.SmartContract{})
	if err != nil {
		log.Panicf("Error creating voting chaincode: %v", err)
	}

	if err := votingChaincode.Start(); err != nil {
		log.Panicf("Error starting voting chaincode: %v", err)
	}
}
