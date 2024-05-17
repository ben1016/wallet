#### APIs
The APIs provides the functionalities of the ledger such as assets movement and querying historical balance. 

- **POST /wallets/transfer**

	For asset transfer from one wallet to another, or multiple transfers from one to another
	Sample request body
  ```json
  {
    "transfers": [
		  {
		  "fromWalletId": 1,
		  "toWalletId": 2,
		  "amount": 0.9
	    },
	    {
		  "fromWalletId": 3,
		  "toWalletId": 4,
		  "amount": 1
	    }
	  ]
	}
  ```
	Sample response body
  ```json
	{
    "status": "CLEARED"
	}
  ```

- **GET /wallets/balance**

	For querying the historical balance by a given timestamp

	Sample request body:
  ```json
	{
		"walletId": 1,
		"balanceAsOfTimestamp": "2024-05-17T13:59:39.972Z"
	}
  ```

	Sample response body:
  ```json
	{
      "walletId": "1",
      "balance": 201,
      "balanceUpdatedTimestamp": "2024-05-16T12:59:31.575Z"
	}
  ```

- **POST /account**

	For creating an account
	
	Sample request body:
  ```json
	{
		"ledgerId":"1",
		"status":"OPEN"
	}
  ```

	Sample response body:
  ```json
	{
      "accountId": "bf8cd707-6e20-43ca-945b-5d3cf34d7b36",
      "ledgerId": "1",
      "status": "OPEN",
      "createdTime": 1715849587618,
      "updatedTime": 1715849587618
	}
  ```

 - **PATCH /account**
	
	For changing the state of an account from one to another

	Sample request body
  ```json
	{
		"accountId": "e630fdd3-2e5e-40df-ae04-fe11d398628a",
		"status": "CLOSED"	
	}
  ```

	Sample response body
  ```json
	{
    		"accountId": "e630fdd3-2e5e-40df-ae04-fe11d398628a",
        "ledgerId": "1",
    		"status": "CLOSED",
    		"createdTime": 1715864729230,
    		"updatedTime": 1715864740825
	}
  ```


#### Account lifecycle	
OPEN: postings can happen to/from any wallets of the account </br>
CLOSED: postings cannot happen to/from any wallets of the account

#### Movement lifecycle
PENDING: tranfer of assets is in progress </br>
CLEARED: assset transfer is completed </br>
FAILED: asset trasfer is failed 

#### Database tables
![alt text](https://github.com/ben1016/wallet/blob/master/image/Wallet%20Diagram.png)

#### Events
**Wallet balance updated event** </br>
For any balance change of any wallet, the wallet balance updated event is emitted for its client to listen to

**Asset transfer completed event** </br>
For any movement happening in the ledger, the asset transfer completed event is emitted for its client to listen to

#### Version
For simulation, Spring H2 in memory database is used. The Java version used is 22 and Spring Boot version is 3.2.5. 
