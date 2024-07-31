#!/bin/sh

echo "Aguardando MySQL ficar pronto!"
sleep 5
echo "MySQL está pronto!"

exec "$@"
