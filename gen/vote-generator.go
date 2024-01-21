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
	"strings"
)

func main() {
	h := sha256.New()
	h.Write([]byte("User voted"))
	bs := h.Sum(nil)
	for {
		print("1. Generate public and private key\n2. Generate signature\n3. Verify signature\n4. Exit\n")
		var command string
		_, err := fmt.Scanf("%s", &command)
		if err == nil {
			switch command {
			case "1":
				privateKey, err := ecdsa.GenerateKey(elliptic.P521(), rand.Reader)
				if err == nil {
					fmt.Printf("Public key: %xg%x\n", privateKey.PublicKey.X, privateKey.PublicKey.Y)
					fmt.Printf("Private key: %x\n", privateKey.D)
				}
			case "2":
				X := new(big.Int)
				Y := new(big.Int)
				D := new(big.Int)
				var str string
				print("Input public key\n")
				_, err := fmt.Scan(&str)
				if err != nil {
					continue
				}
				pb := strings.Split(str, "g")
				_, success := X.SetString(pb[0], 16)
				if !success {
					continue
				}
				_, success = Y.SetString(pb[1], 16)
				if !success {
					continue
				}
				print("Input private key\n")
				_, err = fmt.Scan(&str)
				if err != nil {
					continue
				}
				_, success = D.SetString(str, 16)
				if !success {
					continue
				}
				privateKey := &ecdsa.PrivateKey{PublicKey: ecdsa.PublicKey{Curve: elliptic.P521(), X: X, Y: Y}, D: D}

				sig, err := ecdsa.SignASN1(rand.Reader, privateKey, bs)

				if err != nil {
					continue
				}

				println("Signature: ", hex.EncodeToString(sig))
			case "3":
				X := new(big.Int)
				Y := new(big.Int)
				var str string
				print("Input public key\n")
				_, err := fmt.Scan(&str)
				if err != nil {
					continue
				}
				pb := strings.Split(str, "g")
				_, success := X.SetString(pb[0], 16)
				if !success {
					continue
				}
				_, success = Y.SetString(pb[1], 16)
				if !success {
					continue
				}
				print("Input signature\n")
				_, err = fmt.Scan(&str)
				if err != nil {
					continue
				}
				sig, err := hex.DecodeString(str)
				if err != nil {
					continue
				}

				res := ecdsa.VerifyASN1(&ecdsa.PublicKey{Curve: elliptic.P521(), X: X, Y: Y}, bs, sig)

				if res {
					println("Signature correct")
				} else {
					println("Signature incorrect")
				}
			case "4":
				os.Exit(0)
			}
		}
		print("========================\n")
	}
}
