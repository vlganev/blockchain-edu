# blockchain-edu

# Nodes are live. If all nodes are down, blockchain is gone.

API:

/transaction -> request transaction [POST]

/transaction/pending -> check all pending transactions [GET]

/transaction/{transactionHash} -> request info about transaction [POST]

/blocks -> get all blocks [GET]

/blocks/{id} -> get id index [GET]

/nodes/register –> register new node [“http://localhost:1234”] [POST]

/nodes/info -> show all nodes [GET]