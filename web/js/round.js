/* 
 * Copyright (C) 2015 Rui Hu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
function round(num,dec){ 
    var strNum = num + '';/*change decimals into String*/
    var index = strNum.indexOf("."); /*get decimal point position */
    if(index < 0) {
        return num;/*if there is no decimal point, then round into five*/
    }
    var n = strNum.length - index -1;/*get current data after decimal point */
    if(dec < n){ 
        var e = Math.pow(10, dec);
        num = num * e;
        num = Math.round(num);
        return num / e;
    } else { 
        return num;
    } 
} 

