public class DoveTailAlignment
{
	String seq1;
    String seq2;
    int[][] matrix;
    String alphabet;
    int gap_penalty;
    int[][] dp;
    int score;
    String[] finaloutput;
    int[] start = new int[4];
    public DoveTailAlignment(String seq1, String seq2, int[][] matrix, String alphabet, int gap_penalty) {
        this.seq1 = seq1.toUpperCase();
        this.seq2 = seq2.toUpperCase();
        this.matrix = matrix;
        this.alphabet = alphabet;
        this.gap_penalty = gap_penalty;
        this.dp = dynamic();
        this.score = findmaxinlastrowandlastcolumn(dp);
        this.finaloutput = getsequences();
    }
    //Runs in Linear O(n) time
    //Should be faster than the local alignment
    //Finds the max score in the last column and last row
    public int findmaxinlastrowandlastcolumn(int[][] dp) {
    	int max = Integer.MIN_VALUE;
    	for(int i=0;i<dp.length;i++){
    		max = Math.max(max,dp[i][dp[0].length-1]);
    	}
    	for(int j=0;j<dp[0].length;j++){
    		max = Math.max(max,dp[dp.length-1][j]);
    	}
    	return max;
	}

  //The main method to calculate the matrix scores for the sequences
    public int[][] dynamic()
    {
    	int[][] dp = new int[seq1.length()+1][seq2.length()+1];
    	//Fill the first column with zeroes
    	for(int i = 1; i <= seq1.length(); i++)
    	{
            dp[i][0] = 0;
        }
    	//Fill the first row with zeroes
        for(int j = 1; j <= seq2.length(); j++)
        {
            dp[0][j] = 0;
        }
      //Recursive implementation using dp using needleman-wunsch formula
        for(int i = 1; i < seq1.length() + 1; i++)
        {
            for(int j = 1; j < seq2.length() + 1; j++)
            {
            	dp[i][j] = Math.max(Math.max(dp[i][j-1] + gap_penalty,dp[i-1][j] + gap_penalty),
            			dp[i-1][j-1] + getValues(matrix,seq1.charAt(i-1),seq2.charAt(j-1),alphabet));
            }
        }
        return dp;
    }
  //Finds the path and gets the output modified sequences
    public String[] getsequences() {
    	boolean col = false;
        String outputseq1 = "";
        String outputseq2 = "";
        int i = searchlastcolumn(dp);
        int j = dp[0].length-1;
        if(i==-1){
        	 col = true;
        	j = searchlastrow(dp);
        	i = dp.length-1;
        }
        if(col){
        	start[1] = dp.length-1;
        	start[2] = 0;
        	start[3] = j;
        }
        else{
        	start[0] = 0;
        	start[1] = i;
        	start[3] = dp[0].length-1;
        }
        while (i > 0  && j > 0)
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
            if(col){
            start[0] = i;
            }
            else{
            	start[2] = j;
            }
        }
        return new String[] {outputseq1, outputseq2};
    }
  //Helper function for returning the starting position of row
    public int searchlastrow(int[][] dp) {
    	for(int j=0;j<dp[0].length;j++){
    		if(score == dp[dp.length-1][j])
    			return j;
    	}
    	return -1;
	}
  //Helper function for returning the starting position of column
	public int searchlastcolumn(int[][] dp) {
		for(int i=0;i<dp.length;i++){
    		if(score == dp[i][dp[0].length-1])
    			return i;
    	}
		return -1;
	}
	//Helper functions for returning the score
    public int getscores()
    {
        return score;
    }
  //Helper function for returning the aligned strings
    public String[] getoutputsequences()
    {
    	return finaloutput;
    }
    //Helper function for returning the starting and ending sequence position in the local alignment for 2 sequeneces
    public Integer[] getstart(){
		Integer[] ret = new Integer[4];
		ret[0] = start[0];
		ret[1] = start[1];
		ret[2] = start[2];
		ret[3] = start[3];
		return ret;
	}
  //Returns the appropriate value from the matrix for the alphabet pair
    public int getValues(int[][] matrix, char a, char b, String s)
	{
    	int index1 = s.indexOf(a);
    	int index2 = s.indexOf(b);
    	return matrix[index1][index2];
    }
}
