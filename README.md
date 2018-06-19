# blockchain-edu

# Nodes are live. If all nodes are down, blockchain is gone.

API:

/transaction -> request transaction [POST]

/transaction/pending -> check all pending transactions [GET]

/transaction/{transactionHash} -> request info about transaction [POST]
Example:

{
        "sender": "sender",
        "recipient": "recipient",
        "amount": 100,
        "senderPublickKey": "senderPublickKey",
        "senderSignature": "senderSignature"
}

/blocks -> get all blocks [GET]

/blocks/{id} -> get id index [GET]

/nodes/register –> register new node [“http://localhost:1234”] [POST]

/nodes/info -> show all nodes [GET]

/nodes/sync -> manual sync of the nodes [GET]

/get-miner-job -> get miner job [GET]

/get-block/{id} -> apply for the next block [POST]