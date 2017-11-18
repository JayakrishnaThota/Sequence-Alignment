import java.util.*;
public class initialiser
{
    public static void main(String[] args) throws Exception
    {
    	helper h = new helper();
    	PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Collections.reverseOrder());
    	Scanner in = new Scanner(System.in);
    	int selector = in.nextInt();
        String queryFile = args[0];
        String databaseFile = args[1];
        String alphabetFile = args[2];
        String scorematrixFile = args[3];
        String[] queryRecords = h.getrecords(queryFile);
        String[] databaseRecords = h.getrecords(databaseFile);
        String alphabets = h.getalphabet(alphabetFile);
        int[][] matrix = h.getmatrix(scorematrixFile);
        int k = in.nextInt();
        int m = in.nextInt();
        String[] qids = h.getids(queryFile);
        String[] dids = h.getids(databaseFile);
        		Map<Integer, String[]> smap = new HashMap<Integer, String[]>();
        		Map<Integer, String[]> idmap = new HashMap<Integer, String[]>();
        		Map<Integer, Integer[]> offsetmap = new HashMap<Integer, Integer[]>();

        switch(selector){
        case 1:
        	for(int i=0;i<queryRecords.length;i++){
            	for(int j=0;j<databaseRecords.length;j++){
            		String query = queryRecords[i];
            		String sequence = databaseRecords[j];
            		NeedlemanWunsch n = new NeedlemanWunsch(query, sequence, matrix, alphabets,m);
            		int temp = n.getscores();
            		smap.put(temp, n.getoutputsequences());
            		idmap.put(temp, new String[]{qids[i],dids[j]});
                    pq.offer(temp);
            	}
            }
            for(int i=0;i<k;i++){
            	int temp = pq.poll();
            	System.out.println("score = "+temp);
            	System.out.println("id1 "+idmap.get(temp)[0]+" 0 "+smap.get(temp)[0]);
            	System.out.println("id2 "+idmap.get(temp)[1]+" 0 "+smap.get(temp)[1]);
            }
        	break;
        case 2:
        	for(int i=0;i<queryRecords.length;i++){
            	for(int j=0;j<databaseRecords.length;j++){
            		String query = queryRecords[i];
            		String sequence = databaseRecords[j];
            		SmithWaterman s = new SmithWaterman(query, sequence, matrix, alphabets,m);
            		int temp = s.getscores();
            		smap.put(temp, s.getoutputsequences());
            		idmap.put(temp, new String[]{qids[i],dids[j]});
            		offsetmap.put(temp, s.getstart());
                    pq.offer(temp);
            	}
            }
            for(int i=0;i<k;i++){
            	int temp = pq.poll();
            	System.out.println("score = "+temp);
            	System.out.println("id1 "+idmap.get(temp)[0]+" "+offsetmap.get(temp)[0]+" "+offsetmap.get(temp)[1]+" " +smap.get(temp)[0]);
            	System.out.println("id2 "+idmap.get(temp)[1]+" "+offsetmap.get(temp)[2]+" "+offsetmap.get(temp)[3]+" "+smap.get(temp)[1]);
            }
        	break;
        case 3:
        	for(int i=0;i<queryRecords.length;i++){
            	for(int j=0;j<databaseRecords.length;j++){
            		String query = queryRecords[i];
            		String sequence = databaseRecords[j];
            		DoveTailAlignment d = new DoveTailAlignment(query, sequence, matrix, alphabets,m);
            		int temp = d.getscores();
            		smap.put(temp, d.getoutputsequences());
            		idmap.put(temp, new String[]{qids[i],dids[j]});
            		offsetmap.put(temp, d.getstart());
                    pq.offer(temp);
            	}
            }
            for(int i=0;i<k;i++){
            	int temp = pq.poll();
            	System.out.println("score = "+temp);
            	System.out.println("id1 "+idmap.get(temp)[0]+" "+offsetmap.get(temp)[0]+" "+offsetmap.get(temp)[1]+" " +smap.get(temp)[0]);
            	System.out.println("id2 "+idmap.get(temp)[1]+" "+offsetmap.get(temp)[2]+" "+offsetmap.get(temp)[3]+" "+smap.get(temp)[1]);
            }
        	break;
    }
        in.close();
    }
}
