/* Routine for evaluating individuals  */

# include <stdio.h>
# include <stdlib.h>
# include <math.h>

# include "global.h"
# include "rand.h"

/* Routine to evaluate objective function values and constraints for an individual */
void eval (individual *ind)
{
    int j;
    test_problem (ind->xreal, ind->xbin, ind->gene, ind->obj, ind->constr);
    for (j=0; j<nobj; j++)
    {
        ind->ia[j] = (int)floor( (ind->obj[j]-min_obj[j])/epsilon[j] );
    }
    if (ncon==0)
    {
        ind->constr_violation = 0.0;
    }
    else
    {
        ind->constr_violation = 0.0;
        for (j=0; j<ncon; j++)
        {
            if (ind->constr[j]<0.0)
            {
                ind->constr_violation += ind->constr[j];
            }
        }
    }
    currenteval++;
    return;
}
