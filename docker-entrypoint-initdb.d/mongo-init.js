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
		_id: '0b535a79-1d42-450a-9a70-884761336c91',
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
		_id: '5f041f07-72e1-11ee-8b59-0242ac130003',
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
	},
	{
        _id: '0b535a79-1d42-450a-9a70-884761336c91',
        doctorid: 'bc9ce410-9630-4baf-9bf7-ed4cbf736bb4',
        type: 'APPOINTMENT',
        name: 'test add service burner',
        description: 'Ophthalmology',
        estimatedPrice: 26.5,
        isActive: 1,
        createdAt: ISODate("2023-10-29T07:49:29.670Z"),
        _class: 'org.teamseven.hms.backend.catalog.entity.Service'
      },
      {
        _id: 'a803b134-3074-464d-998a-32c789464f03',
        doctorid: '045de8f0-a442-46c7-8ec9-740c525277f3',
        type: 'APPOINTMENT',
        name: 'test add service burner',
        description: 'Ophthalmology',
        estimatedPrice: 26.5,
        isActive: 1,
        createdAt: ISODate("2023-10-29T08:05:23.911Z"),
        _class: 'org.teamseven.hms.backend.catalog.entity.Service'
      },
      {
        _id: '8e39c9aa-64d2-4eac-97ad-13776ba9c3fd',
        doctorid: 'a9cf7f0b-4ab9-415e-b7c1-79cb2fd14354',
        type: 'APPOINTMENT',
        name: 'test add service burner',
        description: 'Ophthalmology',
        estimatedPrice: 26.5,
        isActive: 1,
        createdAt: ISODate("2023-10-29T08:05:43.242Z"),
        _class: 'org.teamseven.hms.backend.catalog.entity.Service'
      },
      {
        _id: '1af986fa-284d-46c9-9d53-3ba6f7815802',
        doctorid: 'f5d3226d-2445-461d-adee-0560abb2a411',
        type: 'APPOINTMENT',
        name: 'test add service burner',
        description: 'DERMATOLOGY',
        estimatedPrice: 26.5,
        isActive: 1,
        createdAt: ISODate("2023-10-29T08:06:00.873Z"),
        _class: 'org.teamseven.hms.backend.catalog.entity.Service'
      },
      {
        _id: '5cb0c4ea-0d95-49f1-8e54-b39c86ac83cc',
        doctorid: 'cb80c40b-6ebe-4cf8-8a9c-875ff78ddc92',
        type: 'APPOINTMENT',
        name: 'test add service burner',
        description: 'PEDIATRY',
        estimatedPrice: 26.5,
        isActive: 1,
        createdAt: ISODate("2023-10-29T08:06:47.429Z"),
        _class: 'org.teamseven.hms.backend.catalog.entity.Service'
      },
      {
        _id: '73a19577-2e53-459d-96a5-ada9082ca909',
        doctorid: 'f439ec76-7542-473d-8b6e-5beb38d97c86',
        type: 'APPOINTMENT',
        name: 'test add service burner',
        description: 'NEUROLOGY',
        estimatedPrice: 26.5,
        isActive: 1,
        createdAt: ISODate("2023-10-29T08:07:10.777Z"),
        _class: 'org.teamseven.hms.backend.catalog.entity.Service'
      }
]);