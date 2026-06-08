rm dico.xls
cat data.csv | head -n 1 | tr ";" "\n" | cat -n |
while read num name;
do
	echo "n$num: $name" >> "dico.xls"
done
