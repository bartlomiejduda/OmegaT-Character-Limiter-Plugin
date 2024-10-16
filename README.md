# OmegaT-Character-Limiter-Plugin

## OmegaT Info

What is OmegaT? It's a great CAT program with many features.

* [OmegaT Website](https://omegat.org/)
* [OmegaT source code](https://github.com/omegat-org/omegat)

## Plugin Info

OmegaT Character Limiter Plugin allows you to limit characters in your
translation projects. Do you want your translation's text length
to be less or equal it's original length? Do you have some global
limit for all segments and don't want to exceed it? Then this plugin
is for you.
It's free, open source and fully configurable, 
so you can adjust it for your needs.

For your convenience there is a new dockable panel implemented
in main OmegaT window. It shows you info about most important
data processed by the plugin, it is source text length, translation length,
global character limit and a percentage showing how much of the characters
are used in the translation and how much space do you still have left:

<img src="img\block_text.gif">

There are few colors available for percentage value:<br>
🟢Green Color (0-60%) lets you know that 
you still have a lot of space to use in your translation.<br>
🟠Orange Color (60-100%) lets you know
that you're getting closer to fill the limit for your translation.<br>
🔴Red Color (100%+) lets you know that
you've already reached your limit, so you should stop translating
and try to rephrase your text.

If you decide to set global character limit, percentage of used
space will be calculated from this value, instead of source
text length of each segment:

<img src="img\global_limit.gif">

For your disposition there is a config menu that allows you to
set various options. You can access this config window from the
"Tools" menu in OmegaT and then by clicking "View CharacterLimiter Config":

<img src="img\config.png">

Here are some extra features that can be configured using config menu:

* Blocking OmegaT text area after reaching 100% of the character limit :no_entry_sign:
* Playing audio sound after reaching 100% of the character limit :musical_note:
* Setting global character limit for all segments :earth_americas:

## Plugin installation and first use

1. Go to "Releases" tab  on this Github repository.
2. Download attached JAR file and save it on your hard drive.
3. Copy JAR file to OmegaT's plugin directory
(default is "C:\Program Files\OmegaT\plugins")
4. Run OmegaT
5. Load new project in OmegaT
6. Go to "Tools" menu to set plugin's options
7. Start translating and using this plugin to limit characters

## Plugin Compatibility

| Plugin Version | OmegaT Version | Is compatible? |
|----------------|----------------|----------------|
| v1.0           | v5.7.1         | No             |
| v1.0           | v6.0.0         | Yes            |
| v1.0           | v6.0.1         | Yes            |

## Building JAR

To build JAR file from the source code just run "mvn package"
command from your IDE.