# RPBot Tool: Table Creator
Table Creator is a tool for a [RPBot](https://github.com/Tybobobo/RPBot).
This tool was made so that anyone could easily create better looking tables (used in RPBot->Table.class).

Contributions are always welcome.

## How to Use:
The tool uses the **first line** as the name of the item you are inserting into the table.

The remaining lines are split up according to where they find the ':'. <br>
The left side of : will be the property name. It is important that all properties START on a new line, similar to the formatting example. 

On the left side of the : will be the description. It does not matter how many newlines you use, as long as you keep the rules with properties being on the beginning of a new line.

**NB: If you remove a row, make sure to reduce the number of lines by one!**

#### Formatting Example:
NAME<br>
PROPERTY 1: DESCRIPTION<br>
PROPERTY 2: DESCRIPTION ON SAME LINE<br>
MORE DESCRIPTION FOR PROPERTY 2<br>
PROPERTY 3: DESCRIPTION FOR THIRD PROPERTY

#### Input Example:
<pre>Coin Purse
Amount: 5 Gold coins, 3 silver nuggets
Description: It is a leathery purse
that can hold up to around 500 pieces, or 5lb.</pre>

![](http://i.imgur.com/KbN5wbe.png)

#### Output Example
![](http://i.imgur.com/kkcVoZl.png)

## License
GPLv3 License

Copyright (C) 2016 Ivan P. Skodje

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

![GPLv3](http://www.gnu.org/graphics/gplv3-127x51.png)


