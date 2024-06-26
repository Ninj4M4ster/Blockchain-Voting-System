import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.grpc.Grpc
import io.grpc.ManagedChannel
import io.grpc.TlsChannelCredentials
import org.hyperledger.fabric.client.*
import org.hyperledger.fabric.client.identity.Identities
import org.hyperledger.fabric.client.identity.Identity
import org.hyperledger.fabric.client.identity.Signer
import org.hyperledger.fabric.client.identity.Signers
import org.hyperledger.fabric.client.identity.X509Identity
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.InvalidKeyException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit

class blockChainConnection @Throws(IOException::class, CertificateException::class, InvalidKeyException::class)
constructor() {
    private val channel: ManagedChannel = newGrpcConnection()

    init {
        val builder = Gateway.newInstance().identity(newIdentity()).signer(newSigner()).connection(channel)
            .evaluateOptions { options -> options.withDeadlineAfter(5, TimeUnit.SECONDS) }
            .endorseOptions { options -> options.withDeadlineAfter(15, TimeUnit.SECONDS) }
            .submitOptions { options -> options.withDeadlineAfter(5, TimeUnit.SECONDS) }
            .commitStatusOptions { options -> options.withDeadlineAfter(1, TimeUnit.MINUTES) }

        val gateway = builder.connect()
        val network = gateway.getNetwork(CHANNEL_NAME)
        contract = network.getContract(CHAINCODE_NAME)
    }

    private fun newGrpcConnection(): ManagedChannel {
        val credentials = TlsChannelCredentials.newBuilder()
            .trustManager(TLS_CERT_PATH.toFile())
            .build()
        return Grpc.newChannelBuilder(PEER_ENDPOINT, credentials)
            .overrideAuthority(OVERRIDE_AUTH)
            .build()
    }

    @Throws(IOException::class, CertificateException::class)
    private fun newIdentity(): Identity {
        val certReader = Files.newBufferedReader(CERT_PATH)
        val certificate = Identities.readX509Certificate(certReader)
        return X509Identity(MSP_ID, certificate)
    }

    @Throws(IOException::class, InvalidKeyException::class)
    private fun newSigner(): Signer {
        val keyReader = Files.newBufferedReader(privateKeyPath)
        val privateKey = Identities.readPrivateKey(keyReader)
        return Signers.newPrivateKeySigner(privateKey)
    }

    @get:Throws(IOException::class)
    private val privateKeyPath: Path
        get() {
            return Files.list(KEY_DIR_PATH).findFirst().orElse(null)
        }

    @Throws(EndorseException::class, CommitException::class, SubmitException::class, CommitStatusException::class)
    fun initElections(voteInfo: String) {
        try {
            contract.submitTransaction("InitElection", voteInfo)
        } catch (e: EndorseException) {
            println(e)
        }
    }
    fun getElectionStatus(){
        try{
            contract.submitTransaction("GetElectionStatus")
        } catch (e: EndorseException) {
            println(e)
        }
    }

    @Throws(EndorseException::class, SubmitException::class, CommitStatusException::class, CommitException::class)
    fun getVoters() {
        println("\n funkcja pokazujaca wszystkich glosujacych \n")
        try {
            val result = contract.submitTransaction("GetVoters")
            println(prettyJson(result))
        } catch (e: EndorseException) {
            println(e)
        }
    }

    @Throws(EndorseException::class, SubmitException::class, CommitStatusException::class, CommitException::class)
    fun addVoter(key: String) {
        try {
            val result = contract.submitTransaction("AddVoter", key)
            println(result)
        } catch (e: EndorseException) {
            println(e)
        }
    }

    @Throws(EndorseException::class, CommitException::class, SubmitException::class, CommitStatusException::class)
    fun addSignature(publicKey: String, signature: String) {
        val result = contract.submitTransaction("AddSignature", publicKey, signature)
        println(prettyJson(result))
    }

    @Throws(EndorseException::class, CommitException::class, SubmitException::class, CommitStatusException::class)
    fun voterExists(publicKey: String) {
        val result = contract.submitTransaction("VoterExists", publicKey)
        println(prettyJson(result))
    }

    @Throws(EndorseException::class, CommitException::class, SubmitException::class, CommitStatusException::class)
    fun canVote(publicKey: String) {
        val result = contract.submitTransaction("CanVote", publicKey)
        println(prettyJson(result))
    }

    private fun prettyJson(json: ByteArray): String {
        return prettyJson(String(json, StandardCharsets.UTF_8))
    }

    private fun prettyJson(json: String): String {
        val parsedJson = JsonParser.parseString(json)
        return gson.toJson(parsedJson)
    }

    companion object {
        private const val MSP_ID = "Org1MSP"
        private const val CHANNEL_NAME = "voters"
        private const val CHAINCODE_NAME = "vote"
        private val CRYPTO_PATH = Paths.get("/home/pawel/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com")
        private val CERT_PATH = CRYPTO_PATH.resolve(Paths.get("users/Admin@org1.example.com/msp/signcerts/Admin@org1.example.com-cert.pem"))
        private val KEY_DIR_PATH = CRYPTO_PATH.resolve(Paths.get("users/Admin@org1.example.com/msp/keystore"))
        private val TLS_CERT_PATH = CRYPTO_PATH.resolve(Paths.get("peers/peer0.org1.example.com/tls/ca.crt"))
        private const val PEER_ENDPOINT = "localhost:7051"
        private const val OVERRIDE_AUTH = "peer0.org1.example.com"
        private lateinit var contract: Contract
        private val gson = GsonBuilder().setPrettyPrinting().create()
    }
}
