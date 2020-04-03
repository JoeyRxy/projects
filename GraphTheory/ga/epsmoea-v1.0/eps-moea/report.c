/* Routines for storing population data into files */

# include <stdio.h>
# include <stdlib.h>
# include <math.h>

# include "global.h"
# include "rand.h"

/* Function to print the information of a population in a file */
void report_pop (individual *ind, FILE *fpt)
{
    int i, j, k;
    for (i=0; i<popsize; i++)
    {
        for (j=0; j<nobj; j++)
        {
            fprintf(fpt,"%e\t",ind[i].obj[j]);
        }
        if (ncon!=0)
        {
            for (j=0; j<ncon; j++)
            {
                fprintf(fpt,"%e\t",ind[i].constr[j]);
            }
        }
        if (nreal!=0)
        {
            for (j=0; j<nreal; j++)
            {
                fprintf(fpt,"%e\t",ind[i].xreal[j]);
            }
        }
        if (nbin!=0)
        {
            for (j=0; j<nbin; j++)
            {
                for (k=0; k<nbits[j]; k++)
                {
                    fprintf(fpt,"%d\t",ind[i].gene[j][k]);
                }
            }
        }
        fprintf(fpt,"%e\n",ind[i].constr_violation);
    }
    return;
}

/* Function to print the information of feasible and non-dominated population in a file */
void report_archive (ind_list *elite, FILE *fpt)
{
    int j, k;
    ind_list *cur;
    cur = elite->child;
    while (cur!=NULL)
    {
        for (j=0; j<nobj; j++)
        {
            fprintf(fpt,"%e\t",cur->ind->obj[j]);
        }
        if (ncon!=0)
        {
            for (j=0; j<ncon; j++)
            {
                fprintf(fpt,"%e\t",cur->ind->constr[j]);
            }
        }
        if (nreal!=0)
        {
            for (j=0; j<nreal; j++)
            {
                fprintf(fpt,"%e\t",cur->ind->xreal[j]);
            }
        }
        if (nbin!=0)
        {
            for (j=0; j<nbin; j++)
            {
                for (k=0; k<nbits[j]; k++)
                {
                    fprintf(fpt,"%d\t",cur->ind->gene[j][k]);
                }
            }
        }
        fprintf(fpt,"%e\n",cur->ind->constr_violation);
        cur = cur->child;
    }
    return;
}
