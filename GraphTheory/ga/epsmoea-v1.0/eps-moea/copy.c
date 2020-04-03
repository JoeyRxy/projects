/* Routine for mergeing two populations */

# include <stdio.h>
# include <stdlib.h>
# include <math.h>

# include "global.h"
# include "rand.h"

/* Routine to copy an individual 'ind1' into another individual 'ind2' */
void copy (individual *ind1, individual *ind2)
{
    int i, j;
    ind2->constr_violation = ind1->constr_violation;
    if (nreal!=0)
    {
        for (i=0; i<nreal; i++)
        {
            ind2->xreal[i] = ind1->xreal[i];
        }
    }
    if (nbin!=0)
    {
        for (i=0; i<nbin; i++)
        {
            ind2->xbin[i] = ind1->xbin[i];
            for (j=0; j<nbits[i]; j++)
            {
                ind2->gene[i][j] = ind1->gene[i][j];
            }
        }
    }
    for (i=0; i<nobj; i++)
    {
        ind2->obj[i] = ind1->obj[i];
        ind2->ia[i] = ind1->ia[i];
    }
    if (ncon!=0)
    {
        for (i=0; i<ncon; i++)
        {
            ind2->constr[i] = ind1->constr[i];
        }
    }
    return;
}
