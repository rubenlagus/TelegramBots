# Telegram Bot Java Library
A simple to use library to create Telegram Bots in Java (Still Beta Version)

## Translations
Bots are now supporting multilanguage. If you want to add your own, feel free to translate at [transifex](https://www.transifex.com/projects/p/telegrambots/)

## Contributions
Feel free to fork this project, work on it and then make a pull request. Most of the times I will accept them if they add something valuable to the code. 

Please, **DO NOT PUSH ANY TOKEN OR API KEY**, I will never accept a pull request with that content.

## Webhooks vs GetUpdates
Both ways are supported (but I still didn't tested webhooks). To change between them, just go to *BuildVars.java* and change variable *useWebHook* value.

I recommend using getUpdates methods. Webhooks only works using a certificated that is not self-signed

## Example bots
Open them and send them */help* command to get some information about their capabilities:

https://telegram.me/weatherbot 

https://telegram.me/directionsbot

https://telegram.me/filesbot

https://telegram.me/TGlanguagesbot

## Telegram Bot API
This library use [Telegram bot API](https://core.telegram.org/bots), you can find more information following the link.

## Questions or Suggestions
Feel free to create issues [here](https://github.com/rubenlagus/TelegramBots/issues) as you need

## Usage with eclipse

Follow the steps created by Rico [here](https://github.com/rubenlagus/TelegramBots/blob/master/eclipse%20configuration.md)

## License 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
