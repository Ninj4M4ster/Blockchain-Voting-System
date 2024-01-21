package main

import (
	"crypto/ecdsa"
	"crypto/elliptic"
	"crypto/rand"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"math/big"
	"os"
)

func main() {
	privateKey, err := ecdsa.GenerateKey(elliptic.P521(), rand.Reader)
	if err != nil {
		os.Exit(1)
	}

	println(privateKey.PublicKey.X.String())
	println(privateKey.PublicKey.Y.String())
	println(privateKey.D.String())

	X := new(big.Int)
	Y := new(big.Int)
	D := new(big.Int)
	print("Input X of public key (first number)\n")
	_, err = fmt.Scan(X)
	if err != nil {
		os.Exit(1)
	}
	print("Input Y of public key (second number)\n")
	_, err = fmt.Scan(Y)
	if err != nil {
		os.Exit(1)
	}
	print("Input private key\n")
	_, err = fmt.Scan(D)
	if err != nil {
		os.Exit(1)
	}
	println(X.String())
	println(Y.String())
	println(D.String())

	privateKey1 := &ecdsa.PrivateKey{PublicKey: ecdsa.PublicKey{Curve: elliptic.P521(), X: X, Y: Y}, D: D}

	println(privateKey.Equal(privateKey1))

	message := "User voted"
	h := sha256.New()
	h.Write([]byte(message))
	bs := h.Sum(nil)

	sig, err := ecdsa.SignASN1(rand.Reader, privateKey1, bs)

	if err != nil {
		os.Exit(0)
	}

	sigstr := hex.EncodeToString((sig))
	sig1, err := hex.DecodeString(sigstr)

	if err != nil {
		os.Exit(1)
	}

	res := ecdsa.VerifyASN1(&ecdsa.PublicKey{Curve: elliptic.P521(), X: X, Y: Y}, bs, sig1)

	println(res)
}
