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

db.services.insertMany([
{
    _id: '5bd1677a-e74d-48da-bfd5-942b883bb78b',
    type: 'TEST',
    name: 'HIV Test',
    description: 'Blood test for HIV test',
    estimatedPrice: 15,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
},
{
    _id: '23882e13-9fa8-4dad-8637-fc0015ca8869',
    type: 'TEST',
    name: 'Blood glucose test',
    description: 'Venous (plasma) glucose test for diabetes screening',
    estimatedPrice: 15,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
},
{
    _id: 'fe8e69a6-98eb-43aa-b470-e317f3c89ede',
    type: 'TEST',
    name: 'Pancreas blood test',
    description: 'Amylase test & lipase test',
    estimatedPrice: 20,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
},
{
    _id: '420d49da-173e-4528-8789-fd8fe43affb3',
    type: 'TEST',
    name: 'Urinalysis',
    description: 'Urinalysis - chemical & microscopic findings',
    estimatedPrice: 8,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
},
{
    _id: 'add03e0e-dcbf-4aa4-a055-d4968dc34a00',
    type: 'TEST',
    name: 'Thyroid blood test',
    description: 'Scan for hypothyroidism or hyperthyroidism',
    estimatedPrice: 15,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
},
{
    _id: 'a48ba27c-7f18-4af4-a9bb-d2ff0741d27b',
    type: 'TEST',
    name: 'Newborn screening',
    description: 'Scan for genetic, metabolic or hormone-related conditions',
    estimatedPrice: 10,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
},
{
    _id: '42ec512a-6fea-427d-a2a4-fb8a429dd36f',
    type: 'TEST',
    name: 'Lactate dehydrogenase (LDH) test',
    description: 'Measures the amount of LDH in blood or other body fluid',
    estimatedPrice: 13,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
},
{
    _id: 'ee72c915-6607-4016-82b3-db57a3c2e52f',
    type: 'TEST',
    name: 'Skin Biopsy',
    description: 'Skin sampling to find the cause of skin rash or irritation',
    estimatedPrice: 13,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
},
{
    _id: '6a81f508-c83a-4a8d-ac1a-82f7fa275a78',
    type: 'TEST',
    name: 'Nasopharyngeal Swab',
    description: 'Nasal swab test to detect multiple respiratory infections',
    estimatedPrice: 8,
    isActive: 1,
    createdAt: ISODate("2023-10-29T07:49:29.670Z"),
    _class: 'org.teamseven.hms.backend.catalog.entity.Service'
}
]);