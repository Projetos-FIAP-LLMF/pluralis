#!/usr/bin/env bash
# wait-for-it.sh

host="$1"
shift
port="$1"
shift
cmd="$@"

until nc -z "$host" "$port"; do
  echo "Esperando $host:$port estar disponível..."
  sleep 2
done

echo "$host:$port está disponível. Iniciando aplicação."
exec $cmd
