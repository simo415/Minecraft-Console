Minecraft Console Mod
=====================

### [Latest Version](http://dl.dropbox.com/u/7974944/Minecraft%20Console/Minecraft_Console_latest.zip) (most likely to be stable, may not be up to date)
### [Latest Build/Snapshot](http://dl.dropbox.com/u/7974944/Minecraft%20Console/Minecraft_Console_Snapshot.zip) (can be a little unstable, almost always up to date)

Also found on the [Minecraft Forums](http://www.minecraftforum.net/topic/680231-100-minecraft-console-v11-updated/)

Features 
--------

 - Scrollable chat window
 - Copy+Paste
 - Input history
 - Very customisable
 - Works in all Single and Multi player game modes
 - Dynamic window resizing
 - No text overflow
 - Keeps history of whole Minecraft session, not just per world
 - Key bindings
 - Username tab completion (in servers)

[![screenshot](http://i.imgur.com/rFDH3.png)](http://www.youtube.com/watch?v=UfY-AnGmJ3w)

*Click the image above for the youtube video*

Install
-------

 1. Install [modloader](http://www.minecraftforum.net/topic/75440-v11-risugamis-mods-everything-updated/)
 2. Install [Minecraft Console](http://dl.dropbox.com/u/7974944/Minecraft%20Console/Minecraft_Console_latest.zip)
 3. (optional) Install [Single Player Commands](http://www.minecraftforum.net/topic/94310-125-single-player-commands-v322-official-download/)
 
Both mods can be installed with the instructions found in the modloader thread.
However, best practise for version 1.2 an up is to put the Minecraft_Console.zip in the .minecraft/mods/ folder

>*Windows:*
>
> 1. Open up %appdata%, if you don't know how to do this, start>run, then type in %appdata%
> 2. Browse to .minecraft/bin
> 3. Open up minecraft.jar with WinRAR or 7zip.
> 4. Drag and drop the necessary files into the jar.
> 5. Delete the META-INF folder in the jar.
> 6. Run Minecraft, enjoy!
>
>*Macintosh:*
>
> 1. Go to Applications>Utilities and open terminal.
> 2. Type in the following, line by line: 
>
>           cd ~
>           mkdir mctmp
>           cd mctmp
>           jar xf ~/Library/Application\ Support/minecraft/bin/minecraft.jar
>
> 3. Outside of terminal, copy all the files and folders into the mctmp directory.
> 4. Back inside terminal, type in the following:
>
>           rm META-INF/MOJANG_C.*
>           jar uf ~/Library/Application\ Support/minecraft/bin/minecraft.jar ./
>           cd ..
>           rm -rf mctmp
>
> 5. Run Minecraft, enjoy!

Configuration
------------

The GUI can be customised quite a bit already, and I am working on more which will allow everyone (if they want) to have a unique looking GUI. The customisation comes from the configuration file located at `.minecraft/mods/console/gui.properties`

### Colors

Within the settings file, any setting that starts with COLOR_ is a color. To edit these colors simply put in your new color in 32 bit unsigned format.

For example, if you wish to change the output box background:
COLOR_OUTPUT_BACKGROUND=0xFFFFFF

This specifies that the OUTPUT background should be white. The bits are specified as below:

0xAARRGGBB

Where
AA = Alpha
RR = Red
GG = Green
BB = Blue

### Key Bindings

The mod now supports binding keys to events, there are currently no commands that allow you to configure this is game though.

To add or change a key binding simply create a new file (or edit the existing) at: `.minecraft/mods/console/bindings.properties`

Each line in the file specifies a key binding the format is expected as below:
`KEY_CODE_LIST=INPUT`

`KEY_CODE_LIST` is a comma separated list of key codes for all the keys which need to be pressed at the same time in order to execute the `INPUT`
`INPUT` is what is executed - it is sent to the server if you're on multi-player or into the chatline if you're on single player.

Examples:
 - 19 is the R key
 - 42,19 is Shift+R keys
 - 42,19,20 is Shift+R+T keys

![Key codes](http://i.imgur.com/2vy5o.png)
[Source](http://www.minecraftwiki.net/wiki/Key_Codes)

Downloads
------------
 - [*Latest version*](http://dl.dropbox.com/u/7974944/Minecraft%20Console/Minecraft_Console_latest.zip)
 - [*Latest Build/Snapshot*](http://dl.dropbox.com/u/7974944/Minecraft%20Console/Minecraft_Console_Snapshot.zip)

 - [Version 1.3](http://dl.dropbox.com/u/7974944/Minecraft%20Console/Minecraft_Console_1.3.zip)
 - [Version 1.2.1](http://dl.dropbox.com/u/7974944/Minecraft%20Console/Minecraft_Console_1.2.1.zip)
 - [Version 1.2 fixed](http://dl.dropbox.com/u/7974944/Minecraft%20Console/Minecraft_Console_1.2.zip)
 - [Version 1.2](http://dl.dropbox.com/u/8016309/Minecraft%20Console/Minecraft_Console_1.2.zip)
 - [Version 1.1](http://dl.dropbox.com/u/8016309/Minecraft%20Console/Minecraft_Console_1.1.zip)
 - [Version 1.0](http://dl.dropbox.com/u/8016309/Minecraft%20Console/Minecraft_Console_1.0.zip)


