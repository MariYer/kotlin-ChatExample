curl -X POST -H "Authorization: key=AAAA2GNfUvM:APA91bFPKjk3I7ezBq2PoUzJ54zz_NFMWSkhHXa7bhDbDILlHTo-cVZKNy1XmmlCeRYeeRTOMLhMktxr1T3nLb7W5qEocmPNmq_SpPzR5yhEtCbhWd2NpUcDUFsInQcR1wsmmWrXN6rc" -H "Content-Type: application/json" -d '{
"to":"ey29BcpfQfaKeSEAJGE89_:APA91bHSNf-Z7tdk_fs9fRNk6WsfhIvVCJqe-rxkiqyX1I8uVTKBLW7OmLPH-NXV--pupd11xyjd1vbBm1uefhFIW6sR3eze2ohxit1IMvT7JrIv0XsOb257Eno1nG7I6aerWB7SZukm"
"data": {
  "type": "action"
  "title": "Новая акция"
  "description": "Очень интересная акция"
  "imageUrl": "https://i.ytimg.com/vi/vqW8Qpeb7fU/hqdefault_live.jpg"
  }
}' https://fcm.googleapis.com/fcm/send -v -i