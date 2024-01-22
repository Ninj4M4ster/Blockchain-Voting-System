Chaincode now works on single channel

Run network: ./network.sh up

Add channel: ./network.sh createChannel -c vote

Installation: ./network.sh deployCC -c vote -ccl go -ccn vote -ccp [path ending in .../vote-chaincode-go]
Installation: ./network.sh deployCC -c vote -ccl go -ccn vote -ccp /Users/mikolajjanusz/Blockchain-Voting-System/chaincode/vote-chaincode-go

Conracts:
InitElection(voteInfo string) -> error
# voteInfo is a placeholder

GetElectionStatus() -> (int, error)
# 0 - ongoing, 1 - not active yet, 2 - finished, 3 - cancelled

AddVoter(publicKey string) -> error
# in future will only work before election is active

RemoveVoter(ctx contractapi.TransactionContextInterface, publicKey string) -> error
# in the future will only work before election is active 

GetVoters(ctx contractapi.TransactionContextInterface) -> ([]*Voter, error)

HasVotingRights(publicKey string) -> (bool, error)
# return true only if publicKey is present in voters

HasVoted(publicKey string) -> (bool, error)
# returns true only if publicKey is present in voters and signature is added

CastVote(ctx contractapi.TransactionContextInterface, publicKey, signature string) -> (bool, error)
# returns true if adding signature was successful, server should then add the vote to awaiting list

FinalizeVote(candidate string, additionalInfo string) -> error
# adds vote, should be used at different time than CastVote, additionalInfo is placeholder for proof

GetResults(ctx contractapi.TransactionContextInterface) ([]*Result, error)
# returns results in format
#	CandidateID: string
#	VoteCount: int

ValidateElection(ctx contractapi.TransactionContextInterface) -> (bool, error)
# validates election
# keep in mind, most of the time this will return false, because of desynchronisation between
# adding signatures and votes