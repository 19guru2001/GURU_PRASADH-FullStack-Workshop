#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: ./file-organizer.sh /path/to/directory"
    exit 1
fi

TARGET_DIR="$1"

if [ ! -d "$TARGET_DIR" ]; then
    echo "Error: Directory does not exist."
    exit 1
fi

cd "$TARGET_DIR" || exit 1

declare -A file_count

for file in *; do
    if [ -f "$file" ]; then
        ext="${file##*.}"

        if [ "$file" = "$ext" ]; then
            ext="no_extension"
        fi

        mkdir -p "$ext"

        mv "$file" "$ext/"

        ((file_count["$ext"]++))
    fi
done

echo "📊 Organization Summary:"
for ext in "${!file_count[@]}"; do
    echo "Organized ${file_count[$ext]} .$ext files"
done
