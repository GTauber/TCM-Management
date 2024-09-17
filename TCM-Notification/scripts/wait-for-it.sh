#!/usr/bin/env bash
TIMEOUT=60
HOST="$1"
PORT="$2"
shift 2
CMD="$@"

echo "Waiting for $HOST:$PORT to be available..."

for i in $(seq 1 $TIMEOUT); do
  nc -z $HOST $PORT && echo "$HOST:$PORT is available!" && exec $CMD
  sleep 1
done

echo "Timed out waiting for $HOST:$PORT"
exit 1