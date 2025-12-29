#!/bin/bash

THRESHOLD=${1:-90}

if ! [[ "$THRESHOLD" =~ ^[0-9]+$ ]] || [ "$THRESHOLD" -le 0 ] || [ "$THRESHOLD" -gt 100 ]; then
    echo "Usage: ./disk-alert.sh [threshold_percentage]"
    echo "Example: ./disk-alert.sh 80"
    exit 1
fi

ALERT=0

df -hP | awk 'NR>1 {print $1, $5}' | while read -r filesystem usage; do
    usage_percent=${usage%\%}

    if [ "$usage_percent" -ge "$THRESHOLD" ]; then
        echo "WARNING: $filesystem is at $usage_percent% (threshold: $THRESHOLD%)"
        ALERT=1
    else
        echo "OK: $filesystem is at $usage_percent%"
    fi
done

exit $ALERT
