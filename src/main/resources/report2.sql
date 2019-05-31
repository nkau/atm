SELECT c.title, c.name, c.surname, ca.display_balance LOAN_BALANCE, AVG(ca.display_balance)  TRANSACTIONAL_BALANCE
FROM CLIENT c, CLIENT_ACCOUNT CA,ACCOUNT_TYPE at where c.client_id = ca.client_id and (at.account_type_code = ca.account_type_code and at.account_type_code in('HLOAN','PLOAN','CCRD') )
group by c.client_id,at.account_type_code