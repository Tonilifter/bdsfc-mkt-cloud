@Library(['pipelineDef@feature/bigdata', 'pipelineLib', 'common'])
import es.atsistemas.ci.carre4.pipeline.definition.PipelineBuilder
import es.atsistemas.ci.carre4.pipeline.definition.ProjectArchitecture
import es.atsistemas.ci.carre4.pipeline.definition.ProjectTechnology
import com.atsistemas.ci.jenkins.Level

PipelineBuilder builder = new PipelineBuilder()

builder.setloggerLevel(Level.DEBUG)

def projectConfiguration = builder.build(ProjectArchitecture.BIGDATA,
        ProjectTechnology.MAVEN,
        [projectCode: 'http://bitbucket.es.wcorp.carrefour.com/projects/BDSFC/repos/bdsfc-mkt-cloud/browse',
         bigdata: [app: 'http://bitbucket.es.wcorp.carrefour.com/projects/BDSFC/repos/bdsfc-mkt-cloud/browse']  ]
)
builder.start(projectConfiguration)
