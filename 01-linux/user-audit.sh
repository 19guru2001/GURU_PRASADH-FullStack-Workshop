#!/bin/bash

echo "=== User Audit Report ==="

TOTAL_USERS=$(wc -l < /etc/passwd)
echo "Total users: $TOTAL_USERS"

SHELL_USERS=$(awk -F: '$7 !~ /(nologin|false)/ {print $1}' /etc/passwd)
SHELL_USER_COUNT=$(echo "$SHELL_USERS" | wc -l)

echo "Users with shell access: $SHELL_USER_COUNT"

NO_PASSWORD_USERS=()

while IFS=: read -r username password _; do
    if [[ "$password" == "" || "$password" == "!" || "$password" == "*" ]]; then
        NO_PASSWORD_USERS+=("$username")
    fi
done < /etc/shadow 2>/dev/null

NO_PASSWORD_COUNT=${#NO_PASSWORD_USERS[@]}
echo "Users without password: $NO_PASSWORD_COUNT"

for user in "${NO_PASSWORD_USERS[@]}"; do
    echo "  - $user"
done

echo "Last login info for shell users:"

for user in $SHELL_USERS; do
    LAST_LOGIN=$(lastlog -u "$user" | awk 'NR==2 {print $4, $5, $6}')
    if [[ "$LAST_LOGIN" == "" ]]; then
        LAST_LOGIN="Never logged in"
    fi
    echo "  - $user: $LAST_LOGIN"
done
