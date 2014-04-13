#!/usr/bin/gnuplot
reset
set terminal png

set xdata time
set timefmt "%Y-%m-%dT%H:%M:%S"
set format x "%d/%m"

set xlabel "Date (day/month)"
set ylabel "Value of transactions"

set title "Transactions over time"
set key below
set grid

plot "../output/transactions.csv" using 1:2 title "Transactions"
