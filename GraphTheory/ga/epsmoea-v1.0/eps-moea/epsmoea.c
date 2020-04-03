/* EPS-MOEA routine (implementation of the 'main' function) */
/* Copyright Kalyanmoy Deb (deb@iitk.ac.in)
 * Released on 9 May 2005
 * Only to be used for academic and non-profit purposes
 *
 * An acknowledgement of this web site and original paper may be
 * placed any technical document developed as a result of use of 
 * this code. 
 *
 * The developer of this code does not take the responsibility of 
 * its malfunctioning to any specific problem. However, the code is
 * tested on a number of test problems.
 *
 * The code is developed by Santosh Tiwari in consultation 
 * with Kalyanmoy Deb.
 */  
# include <stdio.h>
# include <stdlib.h>
# include <math.h>

# include "global.h"
# include "rand.h"

int nreal;
int nbin;
int nobj;
int ncon;
int popsize;
double pcross_real;
double pcross_bin;
double pmut_real;
double pmut_bin;
double eta_c;
double eta_m;
int neval;
int currenteval;
int nbinmut;
int nrealmut;
int nbincross;
int nrealcross;
int *nbits;
int *array;
double *min_realvar;
double *max_realvar;
double *min_binvar;
double *max_binvar;
double *epsilon;
double *min_obj;
int bitlength;
int elite_size;

int main (int argc, char **argv)
{
    int i;
    int index, index1, index2;
    FILE *fpt1;
    FILE *fpt2;
    FILE *fpt3;
    FILE *fpt4;
    FILE *fpt5;
    individual *ea;
    individual *parent1, *parent2, *child1, *child2;
    ind_list *elite, *cur;
    if (argc<2)
    {
        printf("\n Usage ./main random_seed \n");
        exit(1);
    }
    seed = (double)atof(argv[1]);
    if (seed<=0.0 || seed>=1.0)
    {
        printf("\n Entered seed value is wrong, seed value must be in (0,1) \n");
        exit(1);
    }
    fpt1 = fopen("initial_pop.out","w");
    fpt2 = fopen("final_pop.out","w");
    fpt3 = fopen("final_archive.out","w");
    fpt4 = fopen("all_archive.out","w");
    fpt5 = fopen("params.out","w");
    fprintf(fpt1,"# This file contains the data of initial population\n");
    fprintf(fpt2,"# This file contains the data of final population\n");
    fprintf(fpt3,"# This file contains the best obtained solution(s)\n");
    fprintf(fpt4,"# This file contains the data of archive for all generations\n");
    fprintf(fpt5,"# This file contains information about inputs as read by the program\n");
    printf("\n Enter the problem relevant and algorithm relevant parameters ... ");
    printf("\n Enter the population size (>1) : ");
    scanf("%d",&popsize);
    if (popsize<2)
    {
        printf("\n population size read is : %d",popsize);
        printf("\n Wrong population size entered, hence exiting \n");
        exit (1);
    }
    printf("\n Enter the number of function evaluations : ");
    scanf("%d",&neval);
    if (neval<popsize)
    {
        printf("\n number of function evaluations read is : %d",neval);
        printf("\n Wrong nuber of evaluations entered, hence exiting \n");
        exit (1);
    }
    printf("\n Enter the number of objectives (>=2): ");
    scanf("%d",&nobj);
    if (nobj<2)
    {
        printf("\n number of objectives entered is : %d",nobj);
        printf("\n Wrong number of objectives entered, hence exiting \n");
        exit (1);
    }
    epsilon = (double *)malloc(nobj*sizeof(double));
    min_obj = (double *)malloc(nobj*sizeof(double));
    for (i=0; i<nobj; i++)
    {
        printf("\n Enter the value of epsilon[%d] : ",i+1);
        scanf("%lf",&epsilon[i]);
        if (epsilon[i]<=0.0)
        {
            printf("\n Entered value of epsilon[%d] is non-positive, hence exiting\n",i+1);
            exit(1);
        }
        printf("\n Enter the value of min_obj[%d] (if not known, enter 0.0) : ",i+1);
        scanf("%lf",&min_obj[i]);
    }
    printf("\n Enter the number of constraints : ");
    scanf("%d",&ncon);
    if (ncon<0)
    {
        printf("\n number of constraints entered is : %d",ncon);
        printf("\n Wrong number of constraints enetered, hence exiting \n");
        exit (1);
    }
    printf("\n Enter the number of real variables : ");
    scanf("%d",&nreal);
    if (nreal<0)
    {
        printf("\n number of real variables entered is : %d",nreal);
        printf("\n Wrong number of variables entered, hence exiting \n");
        exit (1);
    }
    if (nreal != 0)
    {
        min_realvar = (double *)malloc(nreal*sizeof(double));
        max_realvar = (double *)malloc(nreal*sizeof(double));
        for (i=0; i<nreal; i++)
        {
            printf ("\n Enter the lower limit of real variable %d : ",i+1);
            scanf ("%lf",&min_realvar[i]);
            printf ("\n Enter the upper limit of real variable %d : ",i+1);
            scanf ("%lf",&max_realvar[i]);
            if (max_realvar[i] <= min_realvar[i])
            {
                printf("\n Wrong limits entered for the min and max bounds of real variable %d, hence exiting \n",i+1);
                exit(1);
            }
        }
        printf ("\n Enter the probability of crossover of real variable (0.6-1.0) : ");
        scanf ("%lf",&pcross_real);
        if (pcross_real<0.0 || pcross_real>1.0)
        {
            printf("\n Probability of crossover entered is : %e",pcross_real);
            printf("\n Entered value of probability of crossover of real variables is out of bounds, hence exiting \n");
            exit (1);
        }
        printf ("\n Enter the probablity of mutation of real variables (1/nreal) : ");
        scanf ("%lf",&pmut_real);
        if (pmut_real<0.0 || pmut_real>1.0)
        {
            printf("\n Probability of mutation entered is : %e",pmut_real);
            printf("\n Entered value of probability of mutation of real variables is out of bounds, hence exiting \n");
            exit (1);
        }
        printf ("\n Enter the value of distribution index for crossover (5-20): ");
        scanf ("%lf",&eta_c);
        if (eta_c<=0)
        {
            printf("\n The value entered is : %e",eta_c);
            printf("\n Wrong value of distribution index for crossover entered, hence exiting \n");
            exit (1);
        }
        printf ("\n Enter the value of distribution index for mutation (5-50): ");
        scanf ("%lf",&eta_m);
        if (eta_m<=0)
        {
            printf("\n The value entered is : %e",eta_m);
            printf("\n Wrong value of distribution index for mutation entered, hence exiting \n");
            exit (1);
        }
    }
    printf("\n Enter the number of binary variables : ");
    scanf("%d",&nbin);
    if (nbin<0)
    {
        printf ("\n number of binary variables entered is : %d",nbin);
        printf ("\n Wrong number of binary variables entered, hence exiting \n");
        exit(1);
    }
    if (nbin != 0)
    {
        nbits = (int *)malloc(nbin*sizeof(int));
        min_binvar = (double *)malloc(nbin*sizeof(double));
        max_binvar = (double *)malloc(nbin*sizeof(double));
        for (i=0; i<nbin; i++)
        {
            printf ("\n Enter the number of bits for binary variable %d : ",i+1);
            scanf ("%d",&nbits[i]);
            if (nbits[i] < 1)
            {
                printf("\n Wrong number of bits for binary variable entered, hence exiting");
                exit(1);
            }
            printf ("\n Enter the lower limit of binary variable %d : ",i+1);
            scanf ("%lf",&min_binvar[i]);
            printf ("\n Enter the upper limit of binary variable %d : ",i+1);
            scanf ("%lf",&max_binvar[i]);
            if (max_binvar[i] <= min_binvar[i])
            {
                printf("\n Wrong limits entered for the min and max bounds of binary variable entered, hence exiting \n");
                exit(1);
            }
        }
        printf ("\n Enter the probability of crossover of binary variable (0.6-1.0): ");
        scanf ("%lf",&pcross_bin);
        if (pcross_bin<0.0 || pcross_bin>1.0)
        {
            printf("\n Probability of crossover entered is : %e",pcross_bin);
            printf("\n Entered value of probability of crossover of binary variables is out of bounds, hence exiting \n");
            exit (1);
        }
        printf ("\n Enter the probability of mutation of binary variables (1/nbits): ");
        scanf ("%lf",&pmut_bin);
        if (pmut_bin<0.0 || pmut_bin>1.0)
        {
            printf("\n Probability of mutation entered is : %e",pmut_bin);
            printf("\n Entered value of probability  of mutation of binary variables is out of bounds, hence exiting \n");
            exit (1);
        }
    }
    if (nreal==0 && nbin==0)
    {
        printf("\n Number of real as well as binary variables, both are zero, hence exiting \n");
        exit(1);
    }
    printf("\n Input data successfully entered, now performing initialization \n");
    fprintf(fpt5,"\n Population size = %d",popsize);
    fprintf(fpt5,"\n Number of function evaluations = %d",neval);
    fprintf(fpt5,"\n Number of objective functions = %d",nobj);
    for (i=0; i<nobj; i++)
    {
        fprintf(fpt5,"\n Epsilon for objective %d = %e",i+1,epsilon[i]);
        fprintf(fpt5,"\n Minimum value of objective %d = %e",i+1,min_obj[i]);
    }
    fprintf(fpt5,"\n Number of constraints = %d",ncon);
    fprintf(fpt5,"\n Number of real variables = %d",nreal);
    if (nreal!=0)
    {
        for (i=0; i<nreal; i++)
        {
            fprintf(fpt5,"\n Lower limit of real variable %d = %e",i+1,min_realvar[i]);
            fprintf(fpt5,"\n Upper limit of real variable %d = %e",i+1,max_realvar[i]);
        }
        fprintf(fpt5,"\n Probability of crossover of real variable = %e",pcross_real);
        fprintf(fpt5,"\n Probability of mutation of real variable = %e",pmut_real);
        fprintf(fpt5,"\n Distribution index for crossover = %e",eta_c);
        fprintf(fpt5,"\n Distribution index for mutation = %e",eta_m);
    }
    fprintf(fpt5,"\n Number of binary variables = %d",nbin);
    if (nbin!=0)
    {
        for (i=0; i<nbin; i++)
        {
            fprintf(fpt5,"\n Number of bits for binary variable %d = %d",i+1,nbits[i]);
            fprintf(fpt5,"\n Lower limit of binary variable %d = %e",i+1,min_binvar[i]);
            fprintf(fpt5,"\n Upper limit of binary variable %d = %e",i+1,max_binvar[i]);
        }
        fprintf(fpt5,"\n Probability of crossover of binary variable = %e",pcross_bin);
        fprintf(fpt5,"\n Probability of mutation of binary variable = %e",pmut_bin);
    }
    fprintf(fpt5,"\n Seed for random number generator = %e",seed);
    bitlength = 0;
    if (nbin!=0)
    {
        for (i=0; i<nbin; i++)
        {
            bitlength += nbits[i];
        }
    }
    fprintf(fpt1,"# of objectives = %d, # of constraints = %d, # of real_var = %d, # of bits of bin_var = %d, constr_violation\n",nobj,ncon,nreal,bitlength);
    fprintf(fpt2,"# of objectives = %d, # of constraints = %d, # of real_var = %d, # of bits of bin_var = %d, constr_violation\n",nobj,ncon,nreal,bitlength);
    fprintf(fpt3,"# of objectives = %d, # of constraints = %d, # of real_var = %d, # of bits of bin_var = %d, constr_violation\n",nobj,ncon,nreal,bitlength);
    fprintf(fpt4,"# of objectives = %d, # of constraints = %d, # of real_var = %d, # of bits of bin_var = %d, constr_violation\n",nobj,ncon,nreal,bitlength);
    nbinmut = 0;
    nrealmut = 0;
    nbincross = 0;
    nrealcross = 0;
    currenteval = 0;
    elite_size = 0;
    randomize();
    ea = (individual *)malloc(popsize*sizeof(individual));
    array = (int *)malloc(popsize*sizeof(int));
    for (i=0; i<popsize; i++)
    {
        allocate (&ea[i]);
        initialize(&ea[i]);
        decode(&ea[i]);
        eval(&ea[i]);
    }
    report_pop (ea, fpt1);
    elite = (ind_list *)malloc(sizeof(ind_list));
    elite->ind = (individual *)malloc(sizeof(individual));
    allocate (elite->ind);
    elite->parent = NULL;
    elite->child = NULL;
    insert (elite, &ea[0]);
    for (i=1; i<popsize; i++)
    {
        update_elite (elite, &ea[i]);
    }
    child1 = (individual *)malloc(sizeof(individual));
    allocate (child1);
    child2 = (individual *)malloc(sizeof(individual));
    allocate (child2);
    cur = elite;
    while (currenteval<neval)
    {
        index1 = rnd(0, popsize-1);
        index2 = rnd(0, popsize-1);
        parent1 = tournament (&ea[index1], &ea[index2]);
        index = rnd(0, elite_size-1);
        cur = elite->child;
        for (i=1; i<=index; i++)
        {
            cur=cur->child;
        }
        parent2 = cur->ind;
        crossover (parent1, parent2, child1, child2);
        mutation (child1);
        decode (child1);
        eval (child1);
        update_elite (elite, child1);
        update_pop (ea, child1);
        mutation (child2);
        decode (child2);
        eval (child2);
        update_elite (elite, child2);
        update_pop (ea, child2);
        printf("\n Currenteval = %d and Elite_size = %d",currenteval,elite_size); 
		/* Comment following three lines if information at all
		evaluation is not desired, it will speed up execution of the code */
	fprintf(fpt4,"# eval id = %d\n",currenteval);
		report_archive (elite, fpt4);
		fflush(fpt4);
    }
    printf("\n Generations finished, now reporting solutions");
    report_pop (ea, fpt2);
    report_archive (elite, fpt3);
    if (nreal!=0)
    {
        fprintf(fpt5,"\n Number of crossover of real variable = %d",nrealcross);
        fprintf(fpt5,"\n Number of mutation of real variable = %d",nrealmut);
    }
    if (nbin!=0)
    {
        fprintf(fpt5,"\n Number of crossover of binary variable = %d",nbincross);
        fprintf(fpt5,"\n Number of mutation of binary variable = %d",nbinmut);
    }
    fflush(stdout);
    fflush(fpt1);
    fflush(fpt2);
    fflush(fpt3);
    fflush(fpt4);
    fflush(fpt5);
    fclose(fpt1);
    fclose(fpt2);
    fclose(fpt3);
    fclose(fpt4);
    fclose(fpt5);
    if (nreal!=0)
    {
        free (min_realvar);
        free (max_realvar);
    }
    if (nbin!=0)
    {
        free (min_binvar);
        free (max_binvar);
        free (nbits);
    }
    free (epsilon);
    free (min_obj);
    free (array);
    for (i=0; i<popsize; i++)
    {
        deallocate (&ea[i]);
    }
    free (ea);
    cur = elite->child;
    while (cur!=NULL)
    {
        cur = del(cur);
        cur = cur->child;
    }
    deallocate (elite->ind);
    free (elite->ind);
    free (elite);
    printf("\n Routine successfully exited \n");
    return (0);
}
