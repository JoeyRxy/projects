/* Routines for updating elite and EA populations */

# include <stdio.h>
# include <stdlib.h>
# include <math.h>

# include "global.h"
# include "rand.h"

/* Routine to update archive */
void update_elite (ind_list *elite, individual *ind)
{
    int i, end, flag;
    double d1, d2;
    ind_list *temp;
    temp = elite->child;
    end = 0;
    do
    {
        flag = check_box_dominance (ind, temp->ind);
        switch (flag)
        {
        case 1: /* ind dominates temp->ind */
            {
                temp = del (temp);
                temp = temp->child;
                break;
            }
        case 2: /* temp->ind dominates ind */
            {
                return;
            }
        case 3: /* both are non-dominated and are in different boxes */
            {
                temp = temp->child;
                break;
            }
        case 4: /* both are non-dominated and are in same hyper-box */
            {
                end = 1;
                break;
            }
        }
    }
    while (end!=1 && temp!=NULL);
    if (end==0)
    {
        insert(elite, ind);
    }
    else
    {
        if (flag==4)        /* in same hyperbox */
        {
            flag = check_dominance (ind, temp->ind);
            switch (flag)
            {
            case 1:
                {
                    temp = del(temp);
                    insert (elite, ind);
                    break;
                }
            case -1:
                {
                    return;
                }
            case 0:
                {
                    d1 = 0.0;
                    d2 = 0.0;
                    for (i=0; i<nobj; i++)
                    {
                        d1 += pow(((ind->obj[i]-ind->ia[i])/epsilon[i]),2.0);
                        d2 += pow(((temp->ind->obj[i]-temp->ind->ia[i])/epsilon[i]),2.0);
                    }
                    if (d1<=d2)
                    {
                        temp = del(temp);
                        insert(elite,ind);
                    }
                    break;
                }
            }
        }
    }
    return;
}

/* Routine to update population */
void update_pop (individual *ea, individual *ind)
{
	int size;
	int i;
	int flag;
	size = 0;
	for (i=0; i<popsize; i++)
	{
		flag = check_dominance (ind, &ea[i]);
		switch (flag)
		{
			case 1:
				copy (ind, &ea[i]);
				return;
			case -1:
				return;
			case 0:
				array[size++] = i;
				break;
		}
	}
	if (size>0)
	{
		i = rnd(0,size-1);
		copy (ind, &ea[array[i]]);
	}
	return;
}
