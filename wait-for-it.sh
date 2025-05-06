#!/usr/bin/env bash

host="$1"
port="$2"
shift 2
echo "Aguardando $host:$port estar disponível..."

while ! nc -z "$host" "$port"; do
  echo "Ainda aguardando $host:$port..."
  sleep 2
done

echo "$host:$port está disponível! Iniciando aplicação..."
exec "$@"
