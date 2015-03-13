outdooractive API - Examples (Scala version)
============================

This repository gives you access to the outdooractive API examples scala sources.

See http://developers.outdooractive.com/


Comparison of original Java version with current Scala solution.
Only considered code under Android/MagicOfWinter/src - measured with CLOC (http://cloc.sourceforge.net/).

<table>
    <tr>
        <th>Language</th>
        <th>files</th>
        <th>blank</th>
        <th>comment</th>
        <th>code</th>
    </tr>
    <tr>
        <td>Java</td>
        <td>15</td>
        <td>223</td>
        <td>22</td>
        <td>897</td>
    </tr>
    <tr>
        <td>Scala</td>
        <td>17</td>
        <td>99</td>
        <td>2</td>
        <td>558</td>
    </tr>
</table>

Means that the code in Scala is more than 30% shorter than the original java source. And this is only the beginning - my guess is that with some more refactorings another 10% can be saved - and this for sure without introducing any complex/unreadable constructs; the opposite is true, the resulting code will reveal the intent more clearly.
