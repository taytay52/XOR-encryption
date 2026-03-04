# XOR-encryption 
## Description
A Java command-line tool that encrypts and decrypts messages using XOR 
bitwise operations and a cryptographically secure random key. Built to 
demonstrate symmetric encryption fundamentals and secure coding practices.

## Features
- Encrypts any plaintext message using XOR with a securely generated key
- Decrypts messages using the matching hex key
- Uses `SecureRandom` for unpredictable key generation (not seeded)
- Validates hex input before processing
- Clears sensitive data from memory after use
- Outputs ciphertext and keys in readable hex format

## How It Works
1. User chooses to Encrypt (E) or Decrypt (D)
2. **Encrypt:** converts message to bytes → generates random key via 
   `SecureRandom` → XORs message with key → outputs hex ciphertext + key
3. **Decrypt:** takes hex ciphertext + hex key → XORs to recover 
   original message

## Technologies Used
- Java
- `java.security.SecureRandom`
- Bitwise XOR operations
- Hex encoding/decoding

## How to Run
1. Clone the repository
2. Compile: `javac XOR.java`
3. Run: `java XOR`
4. Enter `E` to encrypt or `D` to decrypt

## Security Notes
- Keys are generated using `SecureRandom` making them cryptographically 
  unpredictable
- Sensitive byte arrays are zeroed out after use to minimize memory exposure
- Key must be saved after encryption — it cannot be recovered

## What I Learned
- Symmetric encryption and one-time pad concepts
- Secure random number generation in Java
- Hex encoding and byte manipulation
- Secure coding practices (memory clearing, input validation)
