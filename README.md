# Chatter
> A Minecraft mod to slightly change the format of the in-game chat

## Features
* Two chat formats dependant on whether the player is a server operator or not
* Supports all colour and formatting codes, accessible by prefixing with `&` (e.g. `&e`)
    * See [Minecraft formatting codes](https://minecraft.gamepedia.com/Formatting_codes)
* Several placeholders that can be used to inject variables into the message
* Configurable regex match and replace
   * See [regexr](https://regexr.com) for help creating expressions
   * Supports capture groups
   * Configuration is in format `[["match", "replace"]...]`
     * _Be mindful of escape sequences_
* Configurable action for clicking on a username in chat

## Placeholders
All placeholders should be wrapped in braces (e.g. "{NAME}").

| Placeholder | Description           |
|-------------|-----------------------|
| NAME        | Sender's display name |
| MESSAGE     | Message body          |

## Example Configuration
```toml
#General settings
[general]
	#Should the mod be enabled?
	enabled = true

#Chat settings
[chat]
	#Chat format used for standard players
	formatGeneric = "&e{NAME} &8>&r {MESSAGE}"
	#Chat format used for server operators
	formatOperator = "&c{NAME} &8>&r {MESSAGE}"
	#Regular expressions to match and replace
	replacements = [
	    ["\\d{3}", "xxx"],
	    ["only \\$\\d+\\.(\\d+)", "&lonly &r\\$0.$1"],
	    ["remove"]
    ]
```
