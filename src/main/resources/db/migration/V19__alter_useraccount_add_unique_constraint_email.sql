ALTER TABLE useraccount
ADD CONSTRAINT usseraccount_unique_email_constraint
UNIQUE (email);