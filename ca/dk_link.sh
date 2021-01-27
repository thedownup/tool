#!/bin/sh
ip=101.132.183.157
password=Zjt618618
dir=/usr/local/ca


if [ ! -d "$dir" ];then
echo ""
echo "$dir , not dir , will create"
echo ""
mkdir -p $dir
else
echo ""
echo "$dir , dir exist , will delete and create"
echo ""
rm -rf $dir
mkdir -p $dir
fi

cd $dir

openssl genrsa -aes256 -passout pass:$password  -out ca-key.pem 4096

openssl req -new -x509 -days 365 -key ca-key.pem -passin pass:$password -sha256 -out ca.pem -subj "/C=NL/ST=./L=./O=./CN=$ip"

openssl genrsa -out server-key.pem 4096

openssl req -subj "/CN=$ip" -sha256 -new -key server-key.pem -out server.csr

echo subjectAltName = IP:$ip,IP:0.0.0.0 >> extfile.cnf

echo extendedKeyUsage = serverAuth >> extfile.cnf

openssl x509 -req -days 365 -sha256 -in server.csr -CA ca.pem -CAkey ca-key.pem -passin "pass:$password" -CAcreateserial -out server-cert.pem -extfile extfile.cnf

openssl genrsa -out key.pem 4096

openssl req -subj '/CN=client' -new -key key.pem -out client.csr

echo extendedKeyUsage = clientAuth >> extfile.cnf

echo extendedKeyUsage = clientAuth > extfile-client.cnf

openssl x509 -req -days 365 -sha256 -in client.csr -CA ca.pem -CAkey ca-key.pem -passin "pass:$password" -CAcreateserial -out cert.pem -extfile extfile-client.cnf

rm -f -v client.csr server.csr extfile.cnf extfile-client.cnf

chmod -v 0400 ca-key.pem key.pem server-key.pem

chmod -v 0444 ca.pem server-cert.pem cert.pem
