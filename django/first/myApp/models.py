from django.db import models

# Create your models here.


class Grades(models.Model):
    gname = models.CharField(max_length=30)
    gdate = models.DateField()
    ggirlnum = models.IntegerField()
    gboynum = models.IntegerField()
    isDelete = models.BooleanField(default=False)

    def __str__(self):
        return "%s--%d--%d" % (self.gname, self.ggirlnum, self.gboynum)


class Students(models.Model):
    sname = models.CharField(max_length=30)
    sgender = models.BooleanField()
    sage = models.IntegerField()
    scontent = models.CharField(max_length=30)
    isDelete = models.BooleanField(default=False)
    sgrade = models.ForeignKey(
        "Grades", on_delete=models.CASCADE)

    def __str__(self):
        return "name:"+sname+" gender:"+sgender
