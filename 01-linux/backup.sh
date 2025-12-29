#!/bin/bash

if [ $# -ne 2 ]; then
    echo "Usage: ./backup.sh <source_directory> <backup_destination>"
    exit 1
fi

SOURCE_DIR="$1"
BACKUP_DIR="$2"

if [ ! -d "$SOURCE_DIR" ]; then
    echo "Error: Source directory does not exist."
    exit 1
fi

mkdir -p "$BACKUP_DIR"

TIMESTAMP=$(date +"%Y%m%d-%H%M%S")
BACKUP_FILE="backup-$TIMESTAMP.tar.gz"
BACKUP_PATH="$BACKUP_DIR/$BACKUP_FILE"

tar -czf "$BACKUP_PATH" -C "$(dirname "$SOURCE_DIR")" "$(basename "$SOURCE_DIR")"

if [ $? -ne 0 ]; then
    echo "Backup failed!"
    exit 1
fi

BACKUP_SIZE=$(du -h "$BACKUP_PATH" | cut -f1)

echo "Backup created: $BACKUP_FILE"
echo "Backup size: $BACKUP_SIZE"

cd "$BACKUP_DIR" || exit 1
ls -1t backup-*.tar.gz | tail -n +6 | xargs -r rm --

echo "Old backups cleaned up (keeping last 5)"
