package affichage_jeu;
import java.util.Arrays;


public class HungarianAlgorithm {
	private double[][] Matrice;
	private final int ligne, colonne, dimension;
	private final double[] int1, int2;
	private final int[] minint;
	private final double[] minint2;
	private final int[] int3, int4;
	private final int[] int_parent;
	private final boolean[] bon;


	public HungarianAlgorithm(int matrixSize) {
		this.dimension = Math.max(matrixSize, matrixSize);
		this.ligne = matrixSize;
		this.colonne = matrixSize;
		this.Matrice = new double[this.dimension][this.dimension];
		int1 = new double[this.dimension];
		int2 = new double[this.dimension];
		minint = new int[this.dimension];
		minint2 = new double[this.dimension];
		bon = new boolean[this.dimension];
		int_parent = new int[this.dimension];
		int3 = new int[this.dimension];
		Arrays.fill(int3, -1);
		int4 = new int[this.dimension];
		Arrays.fill(int4, -1);
	}

	protected void computeInitialFeasibleSolution() {
		for (int j = 0; j < dimension; j++) {
			int2[j] = Double.POSITIVE_INFINITY;
		}
		for (int w = 0; w < dimension; w++) {
			for (int j = 0; j < dimension; j++) {
				if (Matrice[w][j] < int2[j]) {
					int2[j] = Matrice[w][j];
				}
			}
		}
	}


	public int[] execution(double[][] costMatrix) {

		this.Matrice = costMatrix;
		reduce();
		computeInitialFeasibleSolution();
		greedyMatch();

		int w = fetchUnmatchedWorker();
		while (w < dimension) {
			phase_dinit(w);
			phase_dexecution();
			w = fetchUnmatchedWorker();
		}
		int[] result = Arrays.copyOf(int3, ligne);
		for (w = 0; w < result.length; w++) {
			if (result[w] >= colonne) {
				result[w] = -1;
			}
		}
		return result;
	}


	protected void phase_dexecution() {
		while (true) {
			int minSlackWorker = -1, minSlackJob = -1;
			double minSlackValue = Double.POSITIVE_INFINITY;
			for (int j = 0; j < dimension; j++) {
				if (int_parent[j] == -1) {
					if (minint2[j] < minSlackValue) {
						minSlackValue = minint2[j];
						minSlackWorker = minint[j];
						minSlackJob = j;
					}
				}
			}
			if (minSlackValue > 0) {
				updateLabeling(minSlackValue);
			}
			int_parent[minSlackJob] = minSlackWorker;
			if (int4[minSlackJob] == -1) {

				int committedJob = minSlackJob;
				int parentWorker = int_parent[committedJob];
				while (true) {
					int temp = int3[parentWorker];
					match(parentWorker, committedJob);
					committedJob = temp;
					if (committedJob == -1) {
						break;
					}
					parentWorker = int_parent[committedJob];
				}
				return;
			} else {

				int worker = int4[minSlackJob];
				bon[worker] = true;
				for (int j = 0; j < dimension; j++) {
					if (int_parent[j] == -1) {
						double slack = Matrice[worker][j]
								- int1[worker] - int2[j];
						if (minint2[j] > slack) {
							minint2[j] = slack;
							minint[j] = worker;
						}
					}
				}
			}
		}
	}


	protected int fetchUnmatchedWorker() {
		int w;
		for (w = 0; w < dimension; w++) {
			if (int3[w] == -1) {
				break;
			}
		}
		return w;
	}

	protected void greedyMatch() {
		for (int w = 0; w < dimension; w++) {
			for (int j = 0; j < dimension; j++) {
				if (int3[w] == -1
						&& int4[j] == -1
						&& Matrice[w][j] - int1[w] - int2[j] == 0) {
					match(w, j);
				}
			}
		}
	}


	protected void phase_dinit(int w) {
		Arrays.fill(bon, false);
		Arrays.fill(int_parent, -1);
		bon[w] = true;
		for (int j = 0; j < dimension; j++) {
			minint2[j] = Matrice[w][j] - int1[w]
					- int2[j];
			minint[j] = w;
		}
	}


	protected void match(int w, int j) {
		int3[w] = j;
		int4[j] = w;
	}


	protected void reduce() {
		for (int w = 0; w < dimension; w++) {
			double min = Double.POSITIVE_INFINITY;
			for (int j = 0; j < dimension; j++) {
				if (Matrice[w][j] < min) {
					min = Matrice[w][j];
				}
			}
			for (int j = 0; j < dimension; j++) {
				Matrice[w][j] -= min;
			}
		}
		double[] min = new double[dimension];
		for (int j = 0; j < dimension; j++) {
			min[j] = Double.POSITIVE_INFINITY;
		}
		for (int w = 0; w < dimension; w++) {
			for (int j = 0; j < dimension; j++) {
				if (Matrice[w][j] < min[j]) {
					min[j] = Matrice[w][j];
				}
			}
		}
		for (int w = 0; w < dimension; w++) {
			for (int j = 0; j < dimension; j++) {
				Matrice[w][j] -= min[j];
			}
		}
	}

	protected void updateLabeling(double slack) {
		for (int w = 0; w < dimension; w++) {
			if (bon[w]) {
				int1[w] += slack;
			}
		}
		for (int j = 0; j < dimension; j++) {
			if (int_parent[j] != -1) {
				int2[j] -= slack;
			} else {
				minint2[j] -= slack;
			}
		}
	}
}