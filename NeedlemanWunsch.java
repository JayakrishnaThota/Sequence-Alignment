
public class NeedlemanWunsch
{
	String seq1;
    String seq2;
    int[][] matrix;
    String alphabet;
    int gap_penalty;
    int[][] dp;
    int score;
    String[] finaloutput;
    public NeedlemanWunsch(String seq1, String seq2, int[][] matrix, String alphabet, int gap_penalty) {
        this.seq1 = seq1.toUpperCase();
        this.seq2 = seq2.toUpperCase();
        this.matrix = matrix;
        this.alphabet = alphabet;
        this.gap_penalty = gap_penalty;
        this.dp = dynamic();
        this.score = dp[dp.length-1][dp[0].length-1];
        this.finaloutput = getsequences();
    }
    //The main method to calculate the matrix scores for the sequences
    public int[][] dynamic()
    {
        int[][] dp = new int[seq1.length()+1][seq2.length()+1];
        //The first sequence in the first column has a constant values added to the gap penalty
        for (int i = 1; i <= seq1.length(); i++)
        {
            dp[i][0] = dp[i-1][0] + gap_penalty;
        }
      //The first sequence in the first column has a constant values added to the gap penalty
        for (int j = 1; j <= seq2.length(); j++)
        {
            dp[0][j] = dp[0][j-1] + gap_penalty;
        }
        //Recursive implementation using dp using needleman-wunsch algorithm
        for (int i = 1; i <= seq1.length(); i++)
        {
            for (int j = 1; j <= seq2.length(); j++)
            {
                dp[i][j] = Math.max(Math.max(dp[i][j-1] + gap_penalty, dp[i-1][j] + gap_penalty), dp[i-1][j-1] + getValues(matrix,seq1.charAt(i-1),seq2.charAt(j-1),alphabet));
            }
        }
        return dp;
    }
    //Finds the path and gets the output modified sequences
    public String[] getsequences() {
        String outputseq1 = "";
        String outputseq2 = "";
        int i = dp.length - 1;
        int j = dp[0].length - 1;
        while (i != 0  && j != 0)
        {
            if (dp[i-1][j-1] == dp[i][j] - getValues(matrix,seq1.charAt(i-1),seq2.charAt(j-1),alphabet))
            {
                outputseq1 = seq1.charAt(i-1) + outputseq1;
                outputseq2 = seq2.charAt(j-1) + outputseq2;
                i -= 1;
                j -= 1;
            }
            else if (dp[i][j-1] == dp[i][j] - gap_penalty)
            {
                outputseq1 = "-" + outputseq1;
                outputseq2 = seq2.charAt(j-1) + outputseq2;
                j -= 1;
            }
            else {
                outputseq1 = seq1.charAt(i-1) + outputseq1;
                outputseq2 = "-" + outputseq2;
                i -= 1;
            }
        }
        if (i == 0)
        {
            for (int k = 0; k < j; k++) {
                outputseq1 = "-" + outputseq1;
                outputseq2 = seq2.charAt(j-k) + outputseq2;
            }
        }
        else {
            for (int k = 0; k < i; k++) {
                outputseq1 = seq1.charAt(i-k) + outputseq1;
                outputseq2 = "-" + outputseq2;
            }
        }

        return new String[] {outputseq1, outputseq2};
    }
    //Helper Functions
    //Returns the scores
    public int getscores()
    {
        return score;
    }
    //Returns the modified sequences
    public String[] getoutputsequences()
    {
    	return finaloutput;
    }
    //Returns the appropriate value from the matrix for the alphabet pair
    public int getValues(int[][] matrix, char a, char b, String s)
	{
    	int index1 = s.indexOf(a);
    	int index2 = s.indexOf(b);
    	return matrix[index1][index2];
    }
}
