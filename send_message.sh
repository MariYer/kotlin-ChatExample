curl -X POST -H "Authorization: key=AAAA2GNfUvM:APA91bFPKjk3I7ezBq2PoUzJ54zz_NFMWSkhHXa7bhDbDILlHTo-cVZKNy1XmmlCeRYeeRTOMLhMktxr1T3nLb7W5qEocmPNmq_SpPzR5yhEtCbhWd2NpUcDUFsInQcR1wsmmWrXN6rc" -H "Content-Type: application/json" -d '{
"to":"cxKS7K2jTfOb6hchbnPzHj:APA91bGft74qZwSP3FXjZRDhRKJYaUGO2zzFduLSMT5p950UvLzYN3BEvgc2IbYAwLS_Wi7xoCXogpjcwIPjfJjwndgxiPrBYp9JZyZQr5eXWQT3awA_gJEoqjs0jW0VvWn5XQCoMIcT"
"data": {
  "type": "message"
  "userId": "150001"
  "userName": "Иван Петров"
  "createdAt": "1623766904"
  "text": "Привет. Как насчет завтра?"
  }
}' https://fcm.googleapis.com/fcm/send -v -i