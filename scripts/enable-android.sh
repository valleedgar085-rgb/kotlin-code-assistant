#!/bin/bash

# Script to enable Android app module for building
# This modifies settings.gradle.kts to include the :app module

SETTINGS_FILE="settings.gradle.kts"

if [ ! -f "$SETTINGS_FILE" ]; then
    echo "Error: $SETTINGS_FILE not found!"
    exit 1
fi

# Check if already enabled
if grep -q "^include(\":app\")" "$SETTINGS_FILE"; then
    echo "Android app module is already enabled!"
    exit 0
fi

# Enable the module by uncommenting
sed -i 's|^// include(":app")|include(":app")|' "$SETTINGS_FILE"

echo "✓ Android app module enabled!"
echo "You can now build the Android APK with:"
echo "  ./gradlew :app:assembleDebug"
echo ""
echo "To disable the module again, run: ./scripts/disable-android.sh"
