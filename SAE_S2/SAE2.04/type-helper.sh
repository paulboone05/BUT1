for i in $(seq 1 118);
do
	echo -n "n$i : "
	cut -d ';' -f$i fr-esr-parcoursup.csv | head -n 1
	echo "Taille MAX :"
	cut -d ';' -f$i data.csv | wc -L
	echo "Valeur MAX :"
	cut -d ';' -f$i data.csv | sort -n | tail -1
	echo ""
	echo ""
done
