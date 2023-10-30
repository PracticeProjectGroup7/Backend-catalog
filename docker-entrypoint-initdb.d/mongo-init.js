db.createUser(
        {
            user: "user",
            pwd: "pass",
            roles: [
                {
                    role: "readWrite",
                    db: "healdb"
                }
            ]
        }
);

db = new Mongo().getDB("healdb");

db.createCollection('services', { capped: false });