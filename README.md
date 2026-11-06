# RustMC Minecraft Survival Plugin

RustMC is a Minecraft plugin for Paper 1.21.11 that adds a Rust-inspired survival system with thirst management, water bottle recovery, dehydration damage, survival status messages, and commands for checking player thirst.

## Install

1. Build the plugin with Java 21 and Maven:

   ```bash
   mvn clean package
   ```

2. Copy the generated `target/plugin.jar` file into your Paper server's `plugins` directory.

3. Start or restart the server.

4. Confirm that the server is running Paper 1.21.11 or a compatible 1.21 server version.

## Use

1. Join the server. Your thirst starts at 100/100 and is stored with your player data.
2. Use `/rustmc` to view RustMC survival information.
3. Use `/thirst` to view your current thirst level and recovery instructions.
4. Drink a regular Minecraft water bottle to restore 35 thirst points.
5. Thirst decreases by 5 points every 60 seconds for each online player.
6. When thirst reaches 20 or less, the plugin sends critical thirst warnings.
7. When thirst reaches 0, the player takes damage and receives dehydration warnings.
8. Thirst and food levels are shown in the action bar during each thirst update.
9. Player thirst is preserved through the Persistent Data Container and initialized when the player first joins.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/00b96303-8189-457b-826c-8f445b5ed2d5?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=00b96303-8189-457b-826c-8f445b5ed2d5
