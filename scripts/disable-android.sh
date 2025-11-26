#!/bin/bash

# Script to disable Android app module
# This modifies settings.gradle.kts to comment out the :app module

SETTINGS_FILE="settings.gradle.kts"

if [ ! -f "$SETTINGS_FILE" ]; then
    echo "Error: $SETTINGS_FILE not found!"
    exit 1
fi

# Check if already disabled
if grep -q "^// include(\":app\")" "$SETTINGS_FILE"; then
    echo "Android app module is already disabled!"
    exit 0
fi

# Disable the module by commenting it out
sed -i 's|^include(":app")|// include(":app")|' "$SETTINGS_FILE"

echo "✓ Android app module disabled!"
echo "You can now build the CLI tool without Android SDK:"
echo "  ./gradlew build"
echo ""
echo "To enable the module again, run: ./scripts/enable-android.sh"
