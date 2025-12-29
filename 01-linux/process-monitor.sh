#!/bin/bash

if [ $# -ne 2 ]; then
    echo "Usage: ./process-monitor.sh <process_name> <interval_in_seconds>"
    exit 1
fi

PROCESS_NAME="$1"
INTERVAL="$2"

if ! [[ "$INTERVAL" =~ ^[0-9]+$ ]]; then
    echo "Error: Interval must be a positive number"
    exit 1
fi

echo "Monitoring process '$PROCESS_NAME' every $INTERVAL seconds..."
echo "-----------------------------------------------"

while true; do
    TIMESTAMP=$(date "+%Y-%m-%d %H:%M:%S")

    if pgrep "$PROCESS_NAME" > /dev/null; then
        echo "[$TIMESTAMP] $PROCESS_NAME is running"
    else
        echo "[$TIMESTAMP] ALERT: $PROCESS_NAME has stopped!"
        exit 0
    fi

    sleep "$INTERVAL"
done
