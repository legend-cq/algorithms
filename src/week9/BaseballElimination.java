package week9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
	private final Map<String, Integer> teamMap = new HashMap<>();
	private final String[] teams;
	private final int[][] winLoss;
	private final int[][] remaining;
	private int maxWin = 0;
	private String maxTeam;
	private final int teamCount;
	private final Map<String, Iterable<String>> certificationMap = new HashMap<>();
	
	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {
		In in = new In(filename);
		List<String> lines = new ArrayList<>();
		while (in.hasNextLine()) {
			lines.add(in.readLine());
		}
		int teamNum = Integer.parseInt(lines.get(0));
		teamCount = teamNum;
		teams = new String[teamNum];
		winLoss = new int[teamNum][3];
		remaining = new int[teamNum][teamNum];
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i).trim();
			line = line.replaceAll("\\s+", " ");
			String[] values = line.split(" ");
			teamMap.put(values[0], i - 1);
			teams[i - 1] = values[0];
			winLoss[i - 1][0] = Integer.parseInt(values[1]);
			if (winLoss[i - 1][0] > maxWin) {
				maxWin = winLoss[i - 1][0];
				maxTeam = values[0];
			}
			winLoss[i - 1][1] = Integer.parseInt(values[2]);
			winLoss[i - 1][2] = Integer.parseInt(values[3]);
			for (int j = 0; j < teamNum; j++) {
				remaining[i - 1][j] = Integer.parseInt(values[4 + j]);
			}
		}
	}
	// number of teams
	public int numberOfTeams() {
		return teamCount;
	}
	// all teams
	public Iterable<String> teams() {
		return teamMap.keySet();
	}
	// number of wins for given team
	public int wins(String team) {
		Integer id = teamMap.get(team);
		if (id == null) {
			throw new IllegalArgumentException();
		}
		return winLoss[id][0];
	}
	// number of losses for given team
	public int losses(String team) {
		Integer id = teamMap.get(team);
		if (id == null) {
			throw new IllegalArgumentException();
		}
		return winLoss[id][1];
	}
	// number of remaining games for given team
	public int remaining(String team) {
		Integer id = teamMap.get(team);
		if (id == null) {
			throw new IllegalArgumentException();
		}
		return winLoss[id][2];
	}
	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
		Integer id1 = teamMap.get(team1);
		Integer id2 = teamMap.get(team2);
		if (id1 == null || id2 == null) {
			throw new IllegalArgumentException();
		}
		return remaining[id1][id2];
	}
	// is given team eliminated?
	public boolean isEliminated(String team) {
		Integer id = teamMap.get(team);
		if (id == null) {
			throw new IllegalArgumentException();
		}
		if (winLoss[id][0] + winLoss[id][2] < maxWin) {
			return true;
		}
		if (certificationMap.containsKey(team)) {
			return true;
		}
		int start = teamCount;
		int vertexCount = teamCount * (teamCount - 1) / 2 + teamCount + 2;
		int end = vertexCount - 1;
		int index = start;
		int remainingCount = 0;
		FlowNetwork network = new FlowNetwork(vertexCount);
		for (int i = 0; i < numberOfTeams(); i++) {
			if (i != id) {
				if (winLoss[id][0] + winLoss[id][2] - winLoss[i][0] > 0) {
					FlowEdge edge = new FlowEdge(i, end, winLoss[id][0] + winLoss[id][2] - winLoss[i][0]);
					network.addEdge(edge);
				}
				for (int j = i + 1; j < numberOfTeams(); j++) {
					index++;
					if (j != id) {
						if (remaining[i][j] > 0) {
							FlowEdge edge1 = new FlowEdge(start, index, remaining[i][j]);
							FlowEdge edge2 = new FlowEdge(index, i, Double.POSITIVE_INFINITY);
							FlowEdge edge3 = new FlowEdge(index, j, Double.POSITIVE_INFINITY);
							network.addEdge(edge1);
							network.addEdge(edge2);
							network.addEdge(edge3);
							remainingCount += remaining[i][j];
						}
					}
				}
			}
		}
		FordFulkerson flow = new FordFulkerson(network, start, end);
		Integer maxFlow = (int) flow.value();
		if (maxFlow == remainingCount) {
			return false;
		}
		List<String> certification = new ArrayList<>();
		for (int i = 0; i < teamCount; i++) {
			if (flow.inCut(i)) {
				certification.add(teams[i]);
			}
		}
		certificationMap.put(team, certification);
		return true;
	}
	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team) {
		Integer id = teamMap.get(team);
		if (id == null) {
			throw new IllegalArgumentException();
		}
		if (winLoss[id][0] + winLoss[id][2] < maxWin) {
			return Arrays.asList(maxTeam);
		}
		if (isEliminated(team)) {
			return certificationMap.get(team);
		}
		return null;
	} 
	
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}
