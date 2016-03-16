javac cdac/in/gate/result/ResultProcessing.java

#java -Xmx16G cdac/in/gate/result/ResultProcessing -k ../papers/ME/key.csv -r ../papers/ME/response.csv -an -av -ap ../papers/ME/applicant.csv > ../result/ME/analaysis-new.csv
#echo "ME done!"

#java -Xmx16G cdac/in/gate/result/ResultProcessing -k ../papers/ME/key.csv -r ../papers/ME/response.csv -d -ap ../papers/ME/applicant.csv > ../result/ME/details-marks-new.csv
#echo "ME done!"

#java -Xmx16G cdac/in/gate/result/ResultProcessing -k ../papers/ME/key.csv -r ../papers/ME/response.csv -l -ap ../papers/ME/applicant.csv > ../result/ME/difficulty.txt
#echo "ME done!"

#java -Xmx16G cdac/in/gate/result/ResultProcessing -k ../papers/ME/key.csv -r ../papers/ME/response.csv -an -ap ../papers/ME/applicant.csv > ../result/ME/result.txt
#echo "ME done!"

java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/ME/key.csv -r ../papers/ME/response.csv -rv -ap ../papers/ME/applicant.csv > ../result/ME/result-view-new1.csv
echo "ME done!"

#java -Xmx16G cdac/in/gate/result/ResultProcessing -k ../papers/ME/key.csv -r ../papers/ME/response.csv -sv -ap ../papers/ME/applicant.csv > ../result/ME/scorecard.csv
#echo "ME done!"
